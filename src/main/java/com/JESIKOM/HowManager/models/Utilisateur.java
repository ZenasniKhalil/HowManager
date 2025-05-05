package com.JESIKOM.HowManager.models;

import jakarta.persistence.*;

@Entity
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Column(unique = true)
    private String email;

    private String motDePasse;  // Stocké en hashé

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getNom() {
        return nom;
    }
}

