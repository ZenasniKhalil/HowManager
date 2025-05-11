package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.Client;
import com.JESIKOM.HowManager.models.Planning;
import com.JESIKOM.HowManager.repository.ClientRepository;
import com.JESIKOM.HowManager.repository.PlanningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PlanningRepository planningRepository;

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Client addClient(Client client) {
        Client savedClient = clientRepository.save(client);

        Planning planning = new Planning();
        planning.setClient(savedClient);
        planning.setAnnee(LocalDate.now().getYear());
        planning.setSemaine(LocalDate.now().get(ChronoField.ALIGNED_WEEK_OF_YEAR));
        planning.setNote("Nouveau client enregistré");

        planningRepository.save(planning);

        return savedClient;
    }

    @Override
    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void deleteClientById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public List<Client> getClientByNomContains(String name) {
        return clientRepository.findClientByNomContainsIgnoreCase(name);
    }

    @Override
    public List<Client> getClientByEmail(String email) {
        return clientRepository.findClientByEmailIgnoreCase(email);
    }
}
