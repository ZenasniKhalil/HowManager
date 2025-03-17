package com.JESIKOM.HowManager.repository;


import com.JESIKOM.HowManager.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findClientByNameContainsIgnoreCase(String nom);

    List<Client> findClientByEmailIgnoreCase(String email);

    List<Client> findClientByPrenomAndNomAndEmailIgnoreCase(String prenom, String nom, String email);
}
