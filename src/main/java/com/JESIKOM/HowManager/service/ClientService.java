package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.Client;
import com.JESIKOM.HowManager.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements IClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    //Vérif a faire ? (client existe pas etc )
    public Client addClient(Client client) {
        if (isUnique(client)) {
            return clientRepository.save(client);
        }
        return null;
    }



    public Client updateClientById(Long id, Client updatedClient) {
        return clientRepository.findById(id).map(client -> {
            client.setNom(updatedClient.getNom());
            client.setPrenom(updatedClient.getPrenom());
            client.setTelephone(updatedClient.getTelephone());
            client.setEmail(updatedClient.getEmail());
            client.setAdresse(updatedClient.getAdresse());
            client.setDateNaissance(updatedClient.getDateNaissance());
            client.setNationalite(updatedClient.getNationalite());
            client.setNumeroIdentite(updatedClient.getNumeroIdentite());
            client.setTypeIdentite(updatedClient.getTypeIdentite());
            client.setRemarque(updatedClient.getRemarque());
            return clientRepository.save(client);
        }).orElse(null);
    }

    public void deleteClientById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public List<Client> getClientByNomContaining(String name) {
        return clientRepository.findClientByNomContainingIgnoreCase(name);
    }

    @Override
    public List<Client> getClientByEmail(String email) {
        return clientRepository.findClientByEmailIgnoreCase(email);
    }
    private boolean isUnique(Client client) {
        List<Client> homonymes =
                clientRepository.findClientByPrenomAndNomAndEmailIgnoreCase(client.getPrenom(), client.getNom(), client.getEmail());
        return homonymes.isEmpty();
    }

}

