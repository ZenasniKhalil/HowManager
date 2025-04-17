package com.JESIKOM.HowManager.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plage_horaire")
@Inheritance(strategy = InheritanceType.JOINED)
public class PlageHoraire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "weekly_time_slot_id", referencedColumnName = "id")
    WeeklyTimeSlot plage ;

    @Column(nullable = false)
    private String poste;

    @Column
    private String lieu;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String notes;

    public PlageHoraire(WeeklyTimeSlot plage, String poste, String lieu, String notes) {
        this.plage = plage;
        this.poste = poste;
        this.lieu = lieu;
        this.notes = notes;
    }
    public PlageHoraire(WeeklyTimeSlot plage, String poste) {
        this.plage = plage;
        this.poste = poste;
    }
    public PlageHoraire(WeeklyTimeSlot plage,String poste, String lieu) {
        this.plage = plage;
        this.poste = poste;
        this.lieu = lieu;
    }

    public WeeklyTimeSlot getPlage() {
        return plage;
    }

    public void setPlage(WeeklyTimeSlot plage) {
        this.plage = plage;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlageHoraire() {
    }
}
