package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.*;
import com.JESIKOM.HowManager.repository.PlanningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlanningServiceTest {

    Planning planning;

    @InjectMocks
    private PlanningService service;

    @Mock
    private PlanningRepository repository;

    @Mock
    private TacheService tacheService;

    Tache t1, t2;

    @BeforeEach
    void setUp() {
        t1 = new Tache();
        t2 = new Tache();
        t1.setId(1L);
        t2.setId(2L);
        t1.setDateDebut(LocalDate.of(2024, Month.JANUARY, 1));
        t1.setDateFin(LocalDate.of(2024, Month.JANUARY, 1));
        t2.setDateDebut(LocalDate.of(2024, Month.JANUARY, 1));
        t2.setDateFin(LocalDate.of(2024, Month.JANUARY, 1));
        t1.setPlage(
                new WeeklyTimeSlot(DayOfWeek.MONDAY, LocalTime.of(1, 0), DayOfWeek.MONDAY, LocalTime.of(2, 0))
        );
        t2.setPlage(
                new WeeklyTimeSlot(DayOfWeek.MONDAY, LocalTime.of(2, 0), DayOfWeek.MONDAY, LocalTime.of(3, 0))
        );
        t1.setLieu("piece 1");
        t2.setLieu("piece 2");
        t1.setPoste("poste 1");
        t2.setPoste("poste 2");


        planning = new Planning();
        planning.setAnnee(2024);
        planning.setSemaine(1);
        planning.addTache(t1);
        planning.addTache(t2);
    }






    @Test
    void addPlanningPatternTest() {
        //Test exception
        when(tacheService.overlapsWith(any(),any())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> service.addPlanning(planning));
        //test cas valide
        when(tacheService.overlapsWith(any(),any())).thenReturn(false);
        when(repository.save(planning)).thenReturn(planning);
        assertEquals(planning, service.addPlanning(planning));
    }

    @Test
    void deletePlanningPattern() {

    }

    @Test
    void updatePlanningPattern() {
        when(tacheService.overlapsWith(any(),any())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> service.updatePlanning(0,planning));
        //test cas valide
        when(tacheService.overlapsWith(any(),any())).thenReturn(false);
        when(repository.findById(any())).thenReturn(Optional.of(planning));
        when(repository.save(planning)).thenReturn(planning);
        assertEquals(planning, service.updatePlanning(0,planning));

    }

    @Test
    void computeNombreHeuresPlanningTest() {
        when(tacheService.computeDuree(any())).thenReturn(2.0f);
        assertEquals(2.0f*2,service.computeNombreHeuresPlanning(planning));
    }
}
