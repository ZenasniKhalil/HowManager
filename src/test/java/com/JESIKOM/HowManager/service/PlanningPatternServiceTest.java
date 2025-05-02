package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.PlageHoraire;
import com.JESIKOM.HowManager.models.PlanningPattern;
import com.JESIKOM.HowManager.models.WeeklyTimeSlot;
import com.JESIKOM.HowManager.repository.PlanningPatternRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlanningPatternServiceTest {


    @Mock
    PlanningPatternRepository repository;
    @Mock
    PlageHoraireService phService;

    @InjectMocks
    PlanningPatternService service;

    PlanningPattern pp;




    PlageHoraire ph1 = new PlageHoraire(
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

    @BeforeEach
    void setUp() {
        pp= new PlanningPattern();
        pp.setNom("Test");
        pp.setPlagesHoraires(new ArrayList<PlageHoraire>());
        pp.addPlageHoraire(ph1);
        pp.addPlageHoraire(phNoConflictHr);
    }





    @Test
    void addPlanningPatternTest() {
        //Test exception
        when(phService.overlapsWith(any(),any())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> service.addPlanningPattern(pp));
        //test cas valide
        when(phService.overlapsWith(any(),any())).thenReturn(false);
        when(repository.save(pp)).thenReturn(pp);
        assertEquals(pp, service.addPlanningPattern(pp));
    }

    @Test
    void deletePlanningPattern() {

    }

    @Test
    void updatePlanningPattern() {
        when(phService.overlapsWith(any(),any())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> service.updatePlanningPattern(0,pp));
        //test cas valide
        when(phService.overlapsWith(any(),any())).thenReturn(false);
        when(repository.findById(any())).thenReturn(Optional.of(pp));
        when(repository.save(pp)).thenReturn(pp);
        assertEquals(pp, service.updatePlanningPattern(0,pp));

    }

    @Test
    void computeNombreHeuresPlanningTest() {
        when(phService.computeDuree(any())).thenReturn(2.0f);
        assertEquals(2.0f*2,service.computeNombreHeuresPlanningPattern(pp));
    }

    @Test
    void computeNbHeuresWithMajoration() {
    }
}