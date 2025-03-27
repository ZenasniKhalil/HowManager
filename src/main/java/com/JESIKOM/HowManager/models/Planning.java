package com.JESIKOM.HowManager.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
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

    @OneToMany(mappedBy = "planning")
    List<Tache> taches;

    @Lob
    @Column(columnDefinition = "TEXT")
    String note;

}
