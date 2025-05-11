package com.JESIKOM.HowManager.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planning")
public class Planning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "personnel_id")
    private Personnel personnel;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(nullable = false)
    private int annee;

    @Column(nullable = false)
    private int semaine;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "planning_id")
    private List<Tache> taches;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String note;

    public Planning() {
        taches = new ArrayList<>();
    }

    public Personnel getPersonnel() { return personnel; }
    public void setPersonnel(Personnel personnel) { this.personnel = personnel; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public int getAnnee() { return annee; }
    public void setAnnee(int annee) { this.annee = annee; }

    public int getSemaine() { return semaine; }
    public void setSemaine(int semaine) { this.semaine = semaine; }

    public List<Tache> getTaches() { return taches; }
    public void setTaches(List<Tache> taches) { this.taches = taches; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
