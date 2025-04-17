package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.PlageHoraire;
import com.JESIKOM.HowManager.models.PlanningPattern;
import com.JESIKOM.HowManager.models.WeeklyTimeSlot;
import com.JESIKOM.HowManager.repository.PlanningPatternRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PlanningPatternServiceTest {

    @Mock
    PlanningPatternRepository repository;
    @Mock
    PlageHoraireService phService;

    @InjectMocks
    PlanningPatternService service;

    PlanningPattern pp;

    @BeforeEach
     void setUp() {
        pp= new PlanningPattern();
        pp.setNom("Test");
        pp.setPlagesHoraires();

    }



    /*PlageHoraire ph1 = new PlageHoraire(
             new WeeklyTimeSlot(DayOfWeek.TUESDAY, LocalTime.of(1, 0), DayOfWeek.TUESDAY, LocalTime.of(2, 0)),
            "baba");
    PlageHoraire phNoConflictHr = new PlageHoraire(
            new WeeklyTimeSlot(DayOfWeek.TUESDAY, LocalTime.of(2, 0), DayOfWeek.TUESDAY, LocalTime.of(3, 0)),
            "bobo");
    PlageHoraire phNoConflictJ = new PlageHoraire(
            new WeeklyTimeSlot(DayOfWeek.WEDNESDAY, LocalTime.of(0, 0), DayOfWeek.WEDNESDAY, LocalTime.of(3, 0)),
            "bubu");
    PlageHoraire phConflict = new PlageHoraire(
                    new WeeklyTimeSlot(DayOfWeek.TUESDAY, LocalTime.of(1, 30), DayOfWeek.TUESDAY, LocalTime.of(3, 0)),
                    "babu");
*/





    @Test
    void addPlanningPatternTest() {
        when(phService.overlapsWith(any(),any())).thenReturn(true);




    }

    @Test
    void deletePlanningPattern() {
    }

    @Test
    void updatePlanningPattern() {
    }

    @Test
    void computeNombreHeuresPlanning() {
    }

    @Test
    void computeNbHeuresWithMajoration() {
    }
}