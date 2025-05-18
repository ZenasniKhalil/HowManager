package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.Logement;
import com.JESIKOM.HowManager.models.Reservation;
import com.JESIKOM.HowManager.repository.ClientRepository;
import com.JESIKOM.HowManager.repository.LogementRepository;
import com.JESIKOM.HowManager.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService implements IReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LogementRepository logementRepository;


    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }


    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    //return en cas d'invalidité ? exception ?
    public Reservation addReservation(Reservation reservation) {
        if (logementAvailableBetween(reservation.getLogement(),reservation.getDateDebut(),getDate_Fin(reservation)))
            return reservationRepository.save(reservation);
        else return null;
    }


    public Reservation updateReservation(Long id, Reservation updatedReservation) {
        return reservationRepository.findById(id).map(reservation -> {
            reservation.setClient(updatedReservation.getClient());
            reservation.setLogement(updatedReservation.getLogement());
            reservation.setDateReservation(updatedReservation.getDateReservation());
            reservation.setDateDebut(updatedReservation.getDateDebut());
            reservation.setNombreNuits(updatedReservation.getNombreNuits());
            reservation.setNombreAdultes(updatedReservation.getNombreAdultes());
            reservation.setNombreEnfants(updatedReservation.getNombreEnfants());
            reservation.setStatut(updatedReservation.getStatut());
            reservation.setAcompte(updatedReservation.getAcompte());
            reservation.setCodePromotion(updatedReservation.getCodePromotion());
            reservation.setRemarque(updatedReservation.getRemarque());
            reservation.setModePaiement(updatedReservation.getModePaiement());
            reservation.setCheckIn(updatedReservation.getCheckIn());
            reservation.setCheckOut(updatedReservation.getCheckOut());
            return reservationRepository.save(reservation);
        }).orElse(null);
    }

    public List<Reservation> getAllREservationWithNoCheckout(){
        return reservationRepository.findReservationByCheckOutIsNull();
    }


    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public List<Reservation> getReservationsByDate_Debut(LocalDate date) {
        return reservationRepository.findReservationByDateDebut(date);
    }

    @Override
    public List<Reservation> getReservationsByClient(Long iDClient) {

        return reservationRepository.findReservationByClient_Id(iDClient);
    }

    @Override
    public LocalDate getDate_Fin(Reservation reservation) {
        return reservation.getDateDebut().plusDays(reservation.getNombreNuits());
    }

    @Override
    public String exportReservation(Reservation reservation) {
        return "";
    }

    @Override
    public String exportFacturation(Reservation reservation) {
        return "";
    }

    private boolean logementAvailableBetween( Logement logement,LocalDate startDate, LocalDate endDate) {
        List<Reservation> occupiedReservations = reservationRepository.findReservationsByLogement_Numero(logement.getNumero());
        for (Reservation reservation : occupiedReservations) {
            //Invariant superposition de plages horaires
            if ((reservation.getDateDebut().isEqual(endDate) || reservation.getDateDebut().isBefore(endDate)) &&
                    (getDate_Fin(reservation).isEqual(startDate) || getDate_Fin(reservation).isAfter(startDate)))
                return false;
        }
        return true;
    }
    public int getNombreReservationBetween(LocalDate startDate, LocalDate endDate) {
        return reservationRepository.countReservationByDateDebutBetween(startDate, endDate);
    }

    public List<Reservation> getReservationsByLogement_Numero(int logementNum){
        return reservationRepository.findReservationsByLogement_Numero(logementNum);
    }

    public void checkIn(Long id) throws IllegalArgumentException {
        Optional<Reservation> optRes=getReservationById(id);
        if(optRes.isEmpty())
            throw new IllegalArgumentException("Id not found");
        Reservation reservation=optRes.get();
        reservation.setCheckIn(LocalDateTime.now());
        updateReservation((reservation.getId()),reservation);
    }
    public void checkOut(Long id) throws IllegalArgumentException {
        Optional<Reservation> optRes=getReservationById(id);
        if(optRes.isEmpty())
            throw new IllegalArgumentException("Id not found");
        Reservation reservation=optRes.get();
        reservation.setCheckOut(LocalDateTime.now());
        updateReservation((reservation.getId()),reservation);
    }


}
