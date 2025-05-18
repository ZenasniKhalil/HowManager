package com.JESIKOM.HowManager.controllers;
import com.JESIKOM.HowManager.models.Utilisateur;
import org.springframework.stereotype.Component;

@Component
public class sessionUtilisateur {
    private Utilisateur utilisateurConnecte;

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public void setUtilisateurConnecte(Utilisateur utilisateur) {
        this.utilisateurConnecte = utilisateur;
    }
}
