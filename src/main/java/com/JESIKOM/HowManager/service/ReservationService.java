package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.Reservation;
import com.JESIKOM.HowManager.repository.ClientRepository;
import com.JESIKOM.HowManager.repository.LogementRepository;
import com.JESIKOM.HowManager.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
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

    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Long id, Reservation updatedReservation) {
        return reservationRepository.findById(id).map(reservation -> {
            reservation.setClient(updatedReservation.getClient());
            reservation.setLogement(updatedReservation.getLogement());
            reservation.setDateReservation(updatedReservation.getDateReservation());
            reservation.setDateDebut(updatedReservation.getDateDebut());
            reservation.setNombreNuits(updatedReservation.getNombreNuits());
            reservation.setNombrePersonnes(updatedReservation.getNombrePersonnes());
            reservation.setStatut(updatedReservation.getStatut());
            reservation.setAcompte(updatedReservation.getAcompte());
            reservation.setRemarque(updatedReservation.getRemarque());
            reservation.setModePaiement(updatedReservation.getModePaiement());
            reservation.setCheckIn(updatedReservation.getCheckIn());
            reservation.setCheckOut(updatedReservation.getCheckOut());
            return reservationRepository.save(reservation);
        }).orElse(null);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

}
