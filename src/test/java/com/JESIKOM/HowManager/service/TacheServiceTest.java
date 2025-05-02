package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.*;
import com.JESIKOM.HowManager.repository.TacheRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TacheServiceTest {

    @Mock
    private TacheRepository tacheRepository;

    @Mock
    private WeeklyTimeSlotService weeklyTimeSlotService;

    @InjectMocks
    private TacheService tacheService;

    private Tache tacheExistante;
    private Tache tacheMiseAJour;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        WeeklyTimeSlot slot = new WeeklyTimeSlot(DayOfWeek.MONDAY,LocalTime.of(9, 0),DayOfWeek.MONDAY,LocalTime.of(17, 0));

        tacheExistante = new Tache();
        tacheExistante.setId(1L);
        tacheExistante.setPlage(slot);
        tacheExistante.setPoste("Ancien poste");
        tacheExistante.setLieu("Ancien lieu");
        tacheExistante.setNotes("Ancienne note");
        tacheExistante.setDateDebut(LocalDate.of(2024, 4, 15));
        tacheExistante.setDateFin(LocalDate.of(2024, 4, 16));
        tacheExistante.setStatus(StatusTache.EN_COURS);

        tacheMiseAJour = new Tache();
        tacheMiseAJour.setPlage(slot);  // même slot, mais on pourrait le changer
        tacheMiseAJour.setPoste("Nouveau poste");
        tacheMiseAJour.setLieu("Nouveau lieu");
        tacheMiseAJour.setNotes("Nouvelle note");
        tacheMiseAJour.setDateDebut(LocalDate.of(2024, 4, 15));
        tacheMiseAJour.setDateFin(LocalDate.of(2024, 4, 16));
        tacheMiseAJour.setStatus(StatusTache.TERMINEE);
    }

    @Test
    void testUpdateTache_WhenTacheExists_ShouldUpdateAndReturn() {
        when(tacheRepository.findById(1L)).thenReturn(Optional.of(tacheExistante));
        when(tacheRepository.save(any(Tache.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Tache updated = tacheService.updateTache(1L, tacheMiseAJour);

        assertNotNull(updated);
        assertEquals("Nouveau poste", updated.getPoste());
        assertEquals("Nouveau lieu", updated.getLieu());
        assertEquals("Nouvelle note", updated.getNotes());
        assertEquals(StatusTache.TERMINEE, updated.getStatus());

        verify(tacheRepository, times(1)).findById(1L);
        verify(tacheRepository, times(1)).save(tacheExistante);
    }

    @Test
    void testUpdateTache_WhenTacheNotFound_ShouldReturnNull() {
        when(tacheRepository.findById(1L)).thenReturn(Optional.empty());

        Tache result = tacheService.updateTache(1L, tacheMiseAJour);

        assertNull(result);
        verify(tacheRepository, never()).save(any());
    }
}
