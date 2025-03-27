package com.JESIKOM.HowManager.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "personnel")
public class Personnel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matricule;

    @Column(name = "date_naissance")
    @Temporal(TemporalType.DATE)
    private LocalDate dateNaissance;

    @Column(nullable = false, length = 50)
    private String nom;

    @Column(nullable = false, length = 50)
    private String prenom;

    @Column(nullable = false,length = 50)
    private String genre;

    @Column(nullable = false, length = 50)
    private String nationalite;

    @Column(nullable = false, unique = true, length = 20)
    private String phone;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String adresse;

    @Column
    private String status;

    @Column(nullable = false)
    private String poste;

    @Column(name = "lien_contrat",nullable = false, length = 300)
    private String LienContrat;

    @ElementCollection
    @CollectionTable(name = "personnel_documents", joinColumns = @JoinColumn(name = "personnel_id"))
    private List<String> personnelDocuments;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "taux_horaire")
    private float tauxHoraire;

    @ElementCollection
    @CollectionTable(name = "personnel_majorations", joinColumns = @JoinColumn(name = "personnel_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "type_majoration")
    @Column(name = "majoration_value")
    private Map<TypeMajoration, Float> majorations;

    @Column(name = "nb_heure_semaine")
    private float nbHeureSemaine;

    @Column(name = "nb_heure_mois")
    private float nbHeureMois;

    @ManyToMany
    @JoinTable(name = "personnel_planning_pattern", // Nom de la table de jointure
            joinColumns = @JoinColumn(name = "personnel_id"), // Clé étrangère vers Personnel
            inverseJoinColumns = @JoinColumn(name = "planning_pattern_id") // Clé étrangère vers PlanningPattern
    )
    private List<PlanningPattern> planningPatterns;

    @OneToMany(mappedBy = "personnel")
    private List<Planning> plannings;

    @ManyToOne
    @JoinColumn(name = "superieur_hierarchique_id")
    private Personnel superieurHierarchique;
}
