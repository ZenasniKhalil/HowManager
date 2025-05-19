package com.JESIKOM.HowManager.repository;

import com.JESIKOM.HowManager.models.Reservation;
import com.JESIKOM.HowManager.models.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation>findReservationByDateDebut(LocalDate date);

    List<Reservation>findReservationByClient_Id(long clientId);

    List<Reservation>findReservationsByLogement_Numero(int logementNum);

    int countReservationByDateDebutBetween(LocalDate startDate, LocalDate endDate);

    List<Reservation>findReservationByStatutIsNot(StatutReservation statut);

    List<Reservation>findReservationByStatutIs(StatutReservation statut);


    @Query("SELECT r FROM Reservation r WHERE MONTH(r.dateReservation) = :mois AND YEAR(r.dateReservation) = :annee")
    List<Reservation> findReservationByMoisAndAnnee(@Param("mois") int mois, @Param("annee") int annee);

    @Query("SELECT COUNT(r) FROM Reservation r WHERE MONTH(r.dateReservation) = :mois AND YEAR(r.dateReservation) = :annee")
    int countReservationByMoisAndAnnee(@Param("mois") int mois, @Param("annee") int annee);
}
