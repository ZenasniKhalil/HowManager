package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.Client;

import java.util.List;
import java.util.Optional;

interface IClientService {

    public List<Client> getAllClients();

    public Optional<Client> getClientById(Long id);

    public Client addClient(Client client);

    public Client updateClient(Client client);

    public void deleteClientById(Long id);

    public List<Client>getClientByNomContains(String name);

    public List<Client>getClientByEmail(String email);

    //Plutot que delete ?
    public void ArchiveClient(Client client);


}
