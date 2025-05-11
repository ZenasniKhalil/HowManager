package com.JESIKOM.HowManager.service;


import com.JESIKOM.HowManager.models.Utilisateur;
import com.JESIKOM.HowManager.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;


@Service
public class AuthentificationService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Utilisateur authentifier(String email, String motDePasse) throws Exception {
        return utilisateurRepository.findByEmail(email)
                .filter(u -> BCrypt.checkpw(motDePasse, u.getMotDePasse()))
                .orElseThrow(() -> new Exception("Email ou mot de passe invalide"));
    }

    public void setUtilisateurRepository(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }
}