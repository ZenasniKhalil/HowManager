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
    @Column(nullable = false)
    StatusTache status;


}
