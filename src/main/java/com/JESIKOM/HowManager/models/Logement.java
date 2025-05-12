package com.JESIKOM.HowManager.models;

import jakarta.persistence.*;

@Entity
@Table(name = "logement")
public class Logement {
    @Id
    @Column(name = "logement_id", nullable = false, unique = true)
    private int numero;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeLogement type;

    @Column(name = "capacite", nullable = false)
    private int capacite;

    @Column(name = "disponible", nullable = false)
    private boolean disponible;

    @Column(name = "propre", nullable = false)
    private boolean propre;

    @Column(name = "commentaire", columnDefinition = "TEXT")
    private String commentaire;

    @Column(name = "prix")
    private double prix;

    public Logement(int numero, TypeLogement type, int capacite, boolean disponible, boolean propre, String commentaire, double prix) {
        this.numero = numero;
        this.type = type;
        this.capacite = capacite;
        this.disponible = disponible;
        this.propre = propre;
        this.commentaire = commentaire;
        this.prix = prix;
    }

    public Logement() {
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public TypeLogement getType() {
        return type;
    }

    public void setType(TypeLogement type) {
        this.type = type;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public boolean isPropre() {
        return propre;
    }

    public String isPropreOuiNon(){
        if(propre){
            return "OUI";
        }
        return "NON";
    }

    public void setPropre(boolean propre) {
        this.propre = propre;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public static Boolean stringToBoolean(String s){
        Boolean res = null;
        switch (s) {
            case "OUI", "oui" :
                res = true;
            break;
            case "NON", "non" :
                res = false;
            break;
            default:
                System.out.println("Choix incorrect ! Soit OUI soit NON");
                break;
        }
        return res;
    }
}


