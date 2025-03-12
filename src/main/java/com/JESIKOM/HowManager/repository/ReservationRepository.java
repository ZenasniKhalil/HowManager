package com.JESIKOM.HowManager.repository;

import com.JESIKOM.HowManager.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation>findReservationByDateDebut(LocalDate date);

    List<Reservation>findReservationByClient_Id(long clientId);

    List<Reservation>findReservationsByLogement_Numero(long logementNum);


}
