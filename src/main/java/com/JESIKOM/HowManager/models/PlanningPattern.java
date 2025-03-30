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
    @Lob
    @Column(columnDefinition = "TEXT")
    String description;

    @OneToMany(mappedBy = "planningpattern")
    List<PlageHoraire> plageHoraires;

    @Lob
    @Column(columnDefinition = "TEXT")
    String note;



}
