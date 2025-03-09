package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.Client;

import java.util.List;
import java.util.Optional;

interface IClientService {

    public List<Client> getAllClients();

    public Optional<Client> getClientById(Long id);

    public Client addClient(Client client);

    public Client updateClientById(Long id, Client updatedClient);

    public void deleteClientById(Long id);

    public List<Client>getClientByNomContaining(String name);

    public List<Client>getClientByEmail(String email);



}
