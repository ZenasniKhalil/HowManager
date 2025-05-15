package com.JESIKOM.HowManager.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @OneToMany(mappedBy = "personnel", fetch = FetchType.EAGER)
    private List<WeeklyTimeSlot> weeklyTimeSlots;


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

    public Personnel() {
        planningPatterns=new ArrayList<>();
        plannings = new ArrayList<>();
        personnelDocuments = new ArrayList<>();
        majorations = new HashMap<>();

    }

    public Long getMatricule() {
        return matricule;
    }

    public void setMatricule(Long matricule) {
        this.matricule = matricule;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getLienContrat() {
        return LienContrat;
    }

    public void setLienContrat(String lienContrat) {
        LienContrat = lienContrat;
    }

    public List<String> getPersonnelDocuments() {
        return personnelDocuments;
    }

    public void setPersonnelDocuments(List<String> personnelDocuments) {
        this.personnelDocuments = personnelDocuments;
    }
    public void addPersonnelDocument(String lienDocument){personnelDocuments.add(lienDocument);}

    public void removePersonnelDocument(String lienDocument){personnelDocuments.remove(lienDocument);}

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public float getTauxHoraire() {
        return tauxHoraire;
    }

    public void setTauxHoraire(float tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }

    public Map<TypeMajoration, Float> getMajorations() {
        return majorations;
    }

    public void setMajorations(Map<TypeMajoration, Float> majorations) {
        this.majorations = majorations;
    }

    public float getNbHeureSemaine() {
        return nbHeureSemaine;
    }

    public void setNbHeureSemaine(float nbHeureSemaine) {
        this.nbHeureSemaine = nbHeureSemaine;
    }

    public float getNbHeureMois() {
        return nbHeureMois;
    }

    public void setNbHeureMois(float nbHeureMois) {
        this.nbHeureMois = nbHeureMois;
    }

    public List<PlanningPattern> getPlanningPatterns() {
        return planningPatterns;
    }

    public void setPlanningPatterns(List<PlanningPattern> planningPatterns) {
        this.planningPatterns = planningPatterns;
    }
    public void setWeeklyTimeSlots(List<WeeklyTimeSlot> slots) {
        this.weeklyTimeSlots = slots;
    }
   public List<WeeklyTimeSlot>  getWeeklyTimeSlots(){return this.weeklyTimeSlots; }

    public void addPlanningPattern(PlanningPattern planningPattern) {planningPatterns.add(planningPattern);}

    public void removePlanningPattern(PlanningPattern planningPattern){planningPatterns.remove(planningPattern);}

    public List<Planning> getPlannings() {
        return plannings;
    }

    public void setPlannings(List<Planning> plannings) {
        this.plannings = plannings;
    }

    public void addPlanning(Planning planning) {plannings.add(planning);}

    public void removePlanning(Planning planning){plannings.remove(planning);}

    public Personnel getSuperieurHierarchique() {
        return superieurHierarchique;
    }

    public void setSuperieurHierarchique(Personnel superieurHierarchique) {
        this.superieurHierarchique = superieurHierarchique;
    }
}
