package com.JESIKOM.HowManager.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "tache")
public class Tache  extends PlageHoraire{
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    StatusTache status;
}
