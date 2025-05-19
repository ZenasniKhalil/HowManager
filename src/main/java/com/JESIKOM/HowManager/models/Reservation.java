package com.JESIKOM.HowManager.models;

import jakarta.persistence.*;
import javafx.scene.control.DatePicker;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@ToString
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrémentation
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "logement_id", nullable = false)
    private Logement logement;

    @Column(name = "date_reservation", nullable = false)
    private LocalDateTime dateReservation; // Date et heure de réservation

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "nombre_nuits", nullable = false)
    private int nombreNuits;

    @Column(name = "nombre_adultes", nullable = false)
    private int nombreAdultes;

    @Column(name = "nombre_enfants", nullable = false)
    private int nombreEnfants;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutReservation statut;

    @Column(name = "acompte", nullable = false)
    private double acompte;
    @Column(name = "code_promotion")
    private String codePromotion;

    @Lob
    @Column(name = "remarque", columnDefinition = "TEXT")
    private String remarque;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode_paiement", nullable = false)
    private ModePaiement modePaiement;

    @Column(name = "check_in")
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    private LocalDateTime checkOut;

    public Reservation(Client client, Logement logement, LocalDateTime dateReservation, LocalDate dateDebut, int nombreNuits, int nombreAdultes, int nombreEnfants, StatutReservation statut, double acompte, String remarque, ModePaiement modePaiement, LocalDateTime checkIn, LocalDateTime checkOut ) {
        //this.id = id;
        this.client = client;
        this.logement = logement;
        this.dateReservation = dateReservation;
        this.dateDebut = dateDebut;
        this.nombreNuits = nombreNuits;
        this.nombreAdultes = nombreAdultes;
        this.nombreEnfants = nombreEnfants;
        this.statut = statut;
        this.acompte = acompte;
        this.remarque = remarque;
        this.modePaiement = modePaiement;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Reservation() {

    }

    public String checkInOrCheckOutToString(){
        //
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Logement getLogement() {
        return logement;
    }

    public void setLogement(Logement logement) {
        this.logement = logement;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public int getNombreNuits() {
        return nombreNuits;
    }

    public void setNombreNuits(int nombreNuits) {
        this.nombreNuits = nombreNuits;
    }

    public int getNombreAdultes() {
        return nombreAdultes;
    }

    public void setNombreAdultes(int nombreAdultes) {
        this.nombreAdultes = nombreAdultes;
    }

    public int getNombreEnfants() {
        return nombreEnfants;
    }

    public void setNombreEnfants(int nombreEnfants) {
        this.nombreEnfants = nombreEnfants;
    }

    public StatutReservation getStatut() {
        return statut;
    }

    public void setStatut(StatutReservation statut) {
        this.statut = statut;
    }

    public double getAcompte() {
        return acompte;
    }

    public void setAcompte(double acompte) {
        this.acompte = acompte;
    }

    public String getCodePromotion() {
        return codePromotion;
    }

    public void setCodePromotion(String codePromotion) {
        this.codePromotion = codePromotion;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public ModePaiement getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ModePaiement modePaiement) {
        this.modePaiement = modePaiement;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

}


