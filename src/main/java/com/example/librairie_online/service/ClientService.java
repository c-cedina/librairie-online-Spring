package com.example.librairie_online.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Client;
import com.example.librairie_online.repository.ClientRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClientService {
    private ClientRepository clientRepository;

    // Create
    public void create(Client client) {
        this.clientRepository.save(client);
    }

    // Read
    public List<Client> read() {
        return this.clientRepository.findAll();
    }

    public Client readbyId(int id) {
        Optional<Client> optionalClient = this.clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            return optionalClient.get();
        }
        return null;
    }

    // Update
    public Client update(int id, Client client) {
        Client dbClient = this.readbyId(id);
        dbClient.setAge(client.getAge());
        dbClient.setNom(client.getNom());
        dbClient.setDate_naissance(client.getDate_naissance());
        dbClient.setPrenom(client.getPrenom());
        dbClient.setSexe(null);
        dbClient.setDate_adhesion(null);
        return this.clientRepository.save(dbClient);

    }

    // delete
    public void delete(int id) {
        this.clientRepository.deleteById(id);
    }

}
