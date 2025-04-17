package com.JESIKOM.HowManager.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "planning")
public class Planning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrémentation
    long id;

    @ManyToOne
    @JoinColumn(name = "personnel_id")
    Personnel personnel;
    @Column(nullable = false)
    int annee;

    @Column(nullable = false)
    int semaine;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "planning_id")
    List<Tache> taches;

    @Lob
    @Column(columnDefinition = "TEXT")
    String note;

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getSemaine() {
        return semaine;
    }

    public void setSemaine(int semaine) {
        this.semaine = semaine;
    }

    public List<Tache> getTaches() {
        return taches;
    }

    public void setTaches(List<Tache> taches) {
        this.taches = taches;
    }

    public void addTache(Tache tache) {taches.add(tache);}

    public void removeTache(Tache tache) {taches.remove(tache);}

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
