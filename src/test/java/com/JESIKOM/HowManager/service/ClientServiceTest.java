package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.Client;
import com.JESIKOM.HowManager.models.TypeIdentite;
import com.JESIKOM.HowManager.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    ClientService clientService;

    @Test
    public void addClient(){

        Client client1 = new Client(
                "Dupont",
                "Jean",
                "0601020304",
                "jean.dupont@example.com",
                "12 rue des Lilas, 75001 Paris",
                LocalDate.of(1985, 4, 15),
                "Française",
                "123456789",
                TypeIdentite.PASSEPORT,
                "Client VIP"
        );
        when(clientRepository.findClientByPrenomAndNomAndEmailIgnoreCase(
                client1.getPrenom(), client1.getNom(), client1.getEmail())).thenReturn(List.of());
        when(clientRepository.save(client1)).thenReturn(client1);

        Client res1 = clientService.addClient(client1);
        assertEquals(client1,res1);

        when(clientRepository.findClientByPrenomAndNomAndEmailIgnoreCase(
                client1.getPrenom(), client1.getNom(), client1.getEmail())).thenReturn(List.of(client1));
        Client res3 = clientService.addClient(client1);

        assertNull(res3);
    }
}
