package com.JESIKOM.HowManager.repository;


import com.JESIKOM.HowManager.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
