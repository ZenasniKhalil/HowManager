package com.JESIKOM.HowManager.models;

import jakarta.persistence.*;
import javafx.css.CssParser;
import lombok.Data;

import java.util.List;

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



}
