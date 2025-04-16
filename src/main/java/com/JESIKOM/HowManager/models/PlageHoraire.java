package com.JESIKOM.HowManager.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
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



}
