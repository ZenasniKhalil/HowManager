package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.PlageHoraire;
import com.JESIKOM.HowManager.models.WeeklyTimeSlot;
import com.JESIKOM.HowManager.repository.PlageHoraireRepository;
import com.JESIKOM.HowManager.repository.WeeklyTimeSlotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PlageHoraireServiceTest {

    @Mock
    private WeeklyTimeSlotService wtsService;

    @Mock
    private PlageHoraireRepository repository;

    @InjectMocks
    private PlageHoraireService service;

    PlageHoraire ph1 = new PlageHoraire(
            new WeeklyTimeSlot(DayOfWeek.MONDAY, LocalTime.of(1,0),DayOfWeek.MONDAY,LocalTime.of(2,0)),
            "Recep","maison","test");
    PlageHoraire ph2 = new PlageHoraire(
            new WeeklyTimeSlot(DayOfWeek.MONDAY,LocalTime.of(1,0),DayOfWeek.TUESDAY,LocalTime.of(0,30)),
            "Recep","maison","test2");



    @Test
    void addPlageHoraireTest(){
        //Cas valide
        when(repository.save(ph1)).thenReturn(ph1);
        assertSame(ph1,service.addPlageHoraire(ph1));

    }

    @Test
    void updatePlageHoraireTest() {

    }

    @Test
    void computeDuree() {
        when(wtsService.computeTime(any())).thenReturn(1.f);
        assertEquals(1.f,service.computeDuree(ph1));
        assertEquals(1.f,service.computeDuree(ph2));

    }

    @Test
    void computeHeuresMajorees() {
    }
}