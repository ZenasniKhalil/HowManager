package com.JESIKOM.HowManager.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "plage_horaire")
@Inheritance(strategy = InheritanceType.JOINED)
public class PlageHoraire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "jour_debut")
    private JourSemaine jourDebut;

    @Column(nullable = false,name = "heure_debut")
    private LocalTime heureDebut;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "jour_fin")
    private JourSemaine jourFin;

    @Column(nullable = false,name = "heure_fin")
    private LocalTime heureFin;

    @Column(nullable = false)
    private String poste;

    @Column
    private String lieu;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String notes;


}
