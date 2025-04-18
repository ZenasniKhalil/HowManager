package com.JESIKOM.HowManager.repository;

import com.JESIKOM.HowManager.models.Client;
import com.JESIKOM.HowManager.models.Logement;
import com.JESIKOM.HowManager.models.TypeLogement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class LogementRepositoryTest {
    @Autowired
    private LogementRepository logementRepository;

    private Logement logement1;
    private Logement logement2;

    @BeforeEach
    void setUp() {
        logement1 = new Logement(1, TypeLogement.APPARTEMENT, 4, true, true, "Bien situé", 150.0);
        logement2 = new Logement(2, TypeLogement.STUDIO, 2, false, false, "Petit mais cosy", 80.0);

        logementRepository.save(logement1);
        logementRepository.save(logement2);
    }
    @Test
    void shouldGetAllLogement(){
        List<Logement> logements = logementRepository.findAll();
        assertEquals(2,logements.size());
    }

    @Test
    void shouldFindLogementByCapacite() {
        List<Logement> logements = logementRepository.findLogementByCapacite(4);
        assertEquals(1, logements.size()); // Vérifie qu'un seul logement est retourné
        assertEquals(4, logements.get(0).getCapacite()); // Vérifie la capacité
    }

    @Test
    void shouldFindLogementByDisponible() {
        List<Logement> logements = logementRepository.findLogementByDisponible(true);
        assertEquals(1, logements.size());
        assertTrue(logements.contains(logement1)); // Vérifie si logement1 est bien dans la liste
    }

    @Test
    void shouldFindLogementByType() {
        List<Logement> logements = logementRepository.findLogementByType(TypeLogement.STUDIO);
        assertEquals(1, logements.size());
        assertEquals(TypeLogement.STUDIO, logements.get(0).getType()); // Vérifie le type
    }
}
