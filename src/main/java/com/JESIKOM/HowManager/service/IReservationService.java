package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

interface IReservationService {
    List<Reservation> getAllReservations();

    Optional<Reservation> getReservationById(long id);

    Reservation addReservation(Reservation reservation);

    Reservation updateReservation(long id, Reservation updatedReservation);
    //Archivage ?
    void deleteReservation(long id);

    List<Reservation>getReservationsByDate_Debut(LocalDate date);

    //? Client ou string ?
    List<Reservation>getReservationsByClient(long idClient);

    LocalDate getDate_Fin(Reservation reservation);

    String exportReservation(Reservation reservation);

    String exportFacturation(Reservation reservation);

}
