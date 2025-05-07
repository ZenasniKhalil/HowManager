package com.JESIKOM.HowManager.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrémentation
    private Long id;

    @Column(nullable = false, length = 50)
    private String nom;

    @Column(nullable = false, length = 50)
    private String prenom;

    @Column(nullable = false, unique = true, length = 20)
    private String telephone;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String adresse;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Column( length = 50, nullable = false)
    private String nationalite;

    @Column(name = "num_identite", unique = true, length = 50, nullable = false)
    private String numeroIdentite;

    /**
     * Type pièce d'identité (énuméré)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type_identite", nullable = false)
    private TypeIdentite typeIdentite;

    @Lob
    @Column(name = "remarque", columnDefinition = "TEXT")
    private String remarque;


    public Client() {
        this.nom = "ras";
        this.prenom = "ras";
        this.telephone = "ras";
        this.email = "ras";
        this.adresse = "ras";
        this.dateNaissance = LocalDate.now();
        this.nationalite = "ras";
        this.numeroIdentite = "ras";
        this.typeIdentite = TypeIdentite.CNI;
        this.remarque = "ras";
    }

    public Client( String nom, String prenom, String telephone, String email, String adresse, LocalDate dateNaissance, String nationalite, String numeroIdentite, TypeIdentite typeIdentite, String remarque) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
        this.nationalite = nationalite;
        this.numeroIdentite = numeroIdentite;
        this.typeIdentite = typeIdentite;
        this.remarque = remarque;
    }

    public Client(String nom, String prenom, String telephone, String email, String remarque) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.remarque = remarque;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getNumeroIdentite() {
        return numeroIdentite;
    }

    public void setNumeroIdentite(String numeroIdentite) {
        this.numeroIdentite = numeroIdentite;
    }

    public TypeIdentite getTypeIdentite() {
        return typeIdentite;
    }

    public void setTypeIdentite(TypeIdentite typeIdentite) {
        this.typeIdentite = typeIdentite;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }
}