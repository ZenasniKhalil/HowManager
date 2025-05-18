package com.JESIKOM.HowManager.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "tache")
public class Tache  extends PlageHoraire{
    @Column(nullable = false,name="date_debut")
    LocalDate dateDebut;

    @Column(nullable = false,name="date_fin")
    LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    //@Column(nullable = false)
    StatusTache status;

    String description;

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public StatusTache getStatus() {
        return status;
    }

    public void setStatus(StatusTache status) {
        this.status = status;
    }

    public String getDescrition(){ return description; }

    public void setDescription(String description){ this.description = description; }
    


}
