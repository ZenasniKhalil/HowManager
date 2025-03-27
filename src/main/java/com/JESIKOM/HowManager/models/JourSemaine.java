package com.JESIKOM.HowManager.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public enum JourSemaine {
    LUNDI(DayOfWeek.MONDAY, 1, "Lundi"),
    MARDI(DayOfWeek.TUESDAY, 2, "Mardi"),
    MERCREDI(DayOfWeek.WEDNESDAY, 3, "Mercredi"),
    JEUDI(DayOfWeek.THURSDAY, 4, "Jeudi"),
    VENDREDI(DayOfWeek.FRIDAY, 5, "Vendredi"),
    SAMEDI(DayOfWeek.SATURDAY, 6, "Samedi"),
    DIMANCHE(DayOfWeek.SUNDAY, 7, "Dimanche");

    private final DayOfWeek dayOfWeek;
    private final int numero;
    private final String libelle;

    JourSemaine(DayOfWeek dayOfWeek, int numero, String libelle) {
        this.dayOfWeek = dayOfWeek;
        this.numero = numero;
        this.libelle = libelle;
    }
    public LocalDate toLocalDate(int annee, int semaine) {
        // Trouve la date du premier jour de la semaine donnée
        LocalDate premierJourSemaine = LocalDate.of(annee, 1, 1)
                .with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY))
                .plusWeeks(semaine - 1);

        // Ajuste à notre jour de la semaine spécifique
        return premierJourSemaine.with(TemporalAdjusters.nextOrSame(this.dayOfWeek));

    }
}
