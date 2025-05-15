package com.JESIKOM.HowManager.repository;
import com.JESIKOM.HowManager.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);
    //Optional parce que l'utilisateur n'a pas forcément un compte dans la base
    //Il s'est peut-être connecter avec simplement son email
}

