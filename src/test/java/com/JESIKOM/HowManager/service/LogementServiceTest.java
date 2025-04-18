package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.Logement;
import com.JESIKOM.HowManager.models.TypeLogement;
import com.JESIKOM.HowManager.repository.LogementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LogementServiceTest {
    @Mock
    private LogementRepository logementRepository;

    @InjectMocks
    private LogementService logementService;

    private Logement logement1;
    private Logement logement2;

    @BeforeEach
    void setUp() {
        logement1 = new Logement(1, TypeLogement.APPARTEMENT, 4, true, true, "Bien situé", 150.0);
        logement2 = new Logement(2, TypeLogement.STUDIO, 2, false, false, "Petit mais cosy", 80.0);
    }

    @Test
    void testGetAllLogements() {
        // Arrange: simule la réponse du repository
        when(logementRepository.findAll()).thenReturn(Arrays.asList(logement1, logement2));

        // Act: appel de la méthode du service
        List<Logement> logements = logementService.getAllLogements();

        // Assert: vérification du résultat
        assertEquals(2, logements.size());  // Vérifie que 2 logements sont retournés
        assertTrue(logements.contains(logement1));  // Vérifie que logement1 est présent
        assertTrue(logements.contains(logement2));  // Vérifie que logement2 est présent
    }

    @Test
    void testGetLogementById() {
        // Arrange: simulate repository response
        when(logementRepository.findById(1)).thenReturn(Optional.of(logement1));

        // Act: appel de la méthode du service
        Logement result = logementService.getLogementById(1).orElse(null);

        // Assert: vérification du résultat
        assertNotNull(result);  // Vérifie que le résultat n'est pas null
        assertEquals(1, result.getNumero());  // Vérifie que l'ID est correct
    }

    @Test
    void testUpdateLogement() {
        // Arrange: simulate repository response
        when(logementRepository.findById(1)).thenReturn(Optional.of(logement1));
        Logement updatedLogement = new Logement(1, TypeLogement.APPARTEMENT, 4, true, true, "Nouvelle description", 180.0);

        // Act: appel de la méthode du service
        Logement result = logementService.updateLogement(1, updatedLogement);

        // Assert: vérification que les données sont mises à jour
        assertNotNull(result);  // Vérifie que le logement est trouvé
        assertEquals("Nouvelle description", result.getCommentaire());  // Vérifie le commentaire mis à jour
        assertEquals(180.0, result.getPrix());  // Vérifie le prix mis à jour
    }

    @Test
    void testDeleteLogement() {
        // Arrange: simulate repository behavior
        doNothing().when(logementRepository).deleteById(1);

        // Act: appel de la méthode du service
        logementService.deleteLogement(1);

        // Assert: vérifie que la méthode deleteById a été appelée
        verify(logementRepository, times(1)).deleteById(1);  // Vérifie que deleteById a été appelé exactement 1 fois
    }
}