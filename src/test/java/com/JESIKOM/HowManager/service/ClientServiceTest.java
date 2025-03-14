package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.Client;
import com.JESIKOM.HowManager.models.TypeIdentite;
import com.JESIKOM.HowManager.repository.ClientRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    ClientService clientService;

    Client c1=new Client(
            "Dupont",
            "Jean",
            "0601020304",
            "jean.dupont@example.com",
            "12 rue des Lilas, 75001 Paris",
            LocalDate.of(1985, 4, 15),
            "Française",
            "123456789",
            TypeIdentite.PASSEPORT,
            "Client VIP");
    Client c2=new Client(
            "Martin",
            "Sophie",
            "0612345678",
            "sophie.martin@example.com",
            "45 avenue des Champs-Élysées, 75008 Paris",
            LocalDate.of(1992, 7, 23),
            "Française",
            "987654321",
            TypeIdentite.CIN,
            "Demande une chambre avec vue");

    @Test
    public void addClientTest(){
        //Cas valide
        when(clientRepository.findClientByPrenomAndNomAndEmailIgnoreCase(
                c1.getPrenom(), c1.getNom(), c1.getEmail())).thenReturn(List.of());
        when(clientRepository.save(c1)).thenReturn(c1);

        Client res1 = clientService.addClient(c1);
        assertEquals(c1,res1);
        //Cas doublon
        when(clientRepository.findClientByPrenomAndNomAndEmailIgnoreCase(
                c1.getPrenom(), c1.getNom(), c1.getEmail())).thenReturn(List.of(c1));
        Client res3 = clientService.addClient(c1);

        assertNull(res3);
    }

    @Test
    void getAllClientsTest() {

        //Liste vide
        when(clientRepository.findAll()).thenReturn(List.of());
        assertEquals(0,clientService.getAllClients().size());
        //Liste avec 2 client
        when(clientRepository.findAll()).thenReturn(List.of(c1,c2));
        assertEquals(2,clientService.getAllClients().size());
        //Clients(0)== client1
        assertEquals(c1,clientService.getAllClients().get(0));
    }

    @Test
    void getClientByIdTest() {
        //Cas valide
        when(clientRepository.findById(c1.getId())).thenReturn(Optional.of(c1));
        Optional<Client> res1 =clientService.getClientById(c1.getId());
        assert(res1.isPresent());
        assertEquals(c1,res1.get());
        //Cas invalide
        when(clientRepository.findById(c1.getId())).thenReturn(Optional.empty());
        Optional<Client> res2 =clientService.getClientById(c1.getId());
        assert(res2.isEmpty());
    }

    @Test
    void updateClientById() {


    }

    @Test
    void deleteClientById() {
    }

    @Test
    void getClientByNomContaining() {
    }

    @Test
    void getClientByEmail() {
    }
}
