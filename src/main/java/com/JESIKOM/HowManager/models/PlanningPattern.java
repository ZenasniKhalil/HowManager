package com.JESIKOM.HowManager.models;

import jakarta.persistence.*;
import javafx.css.CssParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="planning_pattern")
public class PlanningPattern {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrémentation
    long id;

    @Column(nullable = false,length =100)
    String nom;

    @Column( length = 4000)  // Spécifier une longueur maximale
    private String description;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "planningPattern_id")
    List<PlageHoraire> plagesHoraires;

    @Lob
    @Column(columnDefinition = "TEXT")
    String note;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PlageHoraire> getPlagesHoraires() {
        return plagesHoraires;
    }

    public void setPlagesHoraires(List<PlageHoraire> plagesHoraires) {
        this.plagesHoraires = plagesHoraires;
    }

    public void addPlageHoraire(PlageHoraire plageHoraire) {plagesHoraires.add(plageHoraire);}

    public void removePlageHoraire(PlageHoraire plageHoraire) {plagesHoraires.remove(plageHoraire);}

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
