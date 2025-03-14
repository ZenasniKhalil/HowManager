package com.JESIKOM.HowManager.repository;


import com.JESIKOM.HowManager.models.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ClientRepositoryTest {
    @Autowired
    private ClientRepository clientRepository;

    @Test
    void shouldGetAllClients(){
        List<Client> clients = clientRepository.findAll();
        assertEquals(5,clients.size());
        assertEquals(1,clients.get(0).getId());
        assertEquals("Dupont",clients.get(0).getNom());

    }
    @Test
    void shouldFindClientByNomContainingIgnoreCase() {
     List<Client> clients = clientRepository.findClientByNomContainingIgnoreCase("existepas");
     assertEquals(0,clients.size());
     List<Client> clients2 = clientRepository.findClientByNomContainingIgnoreCase("Dupont");
     assertEquals(1,clients2.size());
     assertEquals("Dupont",clients2.get(0).getNom());
     List<Client> clients3 = clientRepository.findClientByNomContainingIgnoreCase("Dupon");
     assertEquals(clients2.get(0),clients3.get(0));
     List<Client> clients4 = clientRepository.findClientByNomContainingIgnoreCase("Du");
     assertEquals(2,clients4.size());
     assertEquals("Dupont",clients4.get(0).getNom());
     assertEquals("Dubois",clients4.get(1).getNom());
     List<Client> clients5 = clientRepository.findClientByNomContainingIgnoreCase("dupo");
     assertEquals("Dupont",clients5.get(0).getNom());

    }

    @Test
    void findClientByEmailIgnoreCase() {
        List<Client> clients = clientRepository.findClientByEmailIgnoreCase("jean.dupont@email.com");
        assertEquals(1,clients.size());
        assertEquals("Dupont",clients.get(0).getNom());
        List<Client> clients2 = clientRepository.findClientByEmailIgnoreCase("JeAn.dupont@email.com");
        assertEquals("Dupont",clients.get(0).getNom());
    }

    @Test
    void findClientByPrenomAndNomAndEmailIgnoreCase() {
        List<Client> clients = clientRepository.findClientByPrenomAndNomAndEmailIgnoreCase("Jean","Dupont","jean.dUpont@email.com");
        assertEquals(1,clients.size());
        assertEquals("Dupont",clients.get(0).getNom());
    }
}