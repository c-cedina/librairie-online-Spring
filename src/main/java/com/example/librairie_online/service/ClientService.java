package com.example.librairie_online.service;

import java.util.List;
import java.util.Optional;

import com.example.librairie_online.entity.Role;
import com.example.librairie_online.entity.Validation;
import com.example.librairie_online.enumeration.TypeRole;
import com.example.librairie_online.repository.RoleRepository;
import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Client;
import com.example.librairie_online.repository.ClientRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClientService {
    private ClientRepository clientRepository;
    private ValidationService validationService;
    private RoleRepository roleRepository;

    // Create
    public Client create(Client client) {
        if (this.clientRepository.existsByEmail(client.getEmail())) {
            System.err.println("Client already exists");
            return null;
        }
       Role role = roleRepository.findByRole(TypeRole.USER);
        client.setRole(role);
       Client NewClient =this.clientRepository.save(client);
        Validation validation =validationService.create(NewClient);
        return this.clientRepository.save(validation.getClient());
    }

    // Read
    public List<Client> read() {
        return this.clientRepository.findAll();
    }

    public Client readById(int id) {
        Optional<Client> optionalClient = this.clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            return optionalClient.get();
        }
        return null;
    }

    // Update
    public Client update(int id, Client client) {
        Client dbClient = this.readById(id);
        dbClient.setAge(client.getAge());
        dbClient.setNom(client.getNom());
        dbClient.setDate_naissance(client.getDate_naissance());
        dbClient.setPrenom(client.getPrenom());
        dbClient.setSexe(null);
        dbClient.setDate_adhesion(null);
        dbClient.setEmail(client.getEmail());
        dbClient.setActive(client.isActive());
        return this.clientRepository.save(dbClient);

    }

    // delete
    public void delete(int id) {
        this.clientRepository.deleteById(id);
    }

}
