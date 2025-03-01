package com.JESIKOM.HowManager.repository;

import com.JESIKOM.HowManager.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
