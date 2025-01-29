package com.example.librairie_online.service;

import java.util.List;
import java.util.Optional;

import com.example.librairie_online.entity.Role;
import com.example.librairie_online.entity.Validation;
import com.example.librairie_online.enumeration.TypeRole;
import com.example.librairie_online.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Client;
import com.example.librairie_online.repository.ClientRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    private ClientRepository clientRepository;
    private ValidationService validationService;
    private RoleRepository roleRepository;

    // Create
    public Client create(Client client) {
        logger.info("Création d'un nouveau client: {}", client);

        if (this.clientRepository.existsByEmail(client.getEmail())) {
            logger.warn("Échec de la création: Client avec email '{}' existe déjà.", client.getEmail());
            return null;
        }

        Role role = roleRepository.findByRole(TypeRole.USER);
        if (role == null) {
            logger.info("Rôle USER introuvable, création en cours...");
            role = new Role();
            role.setRole(TypeRole.USER);
            role = roleRepository.save(role);
            logger.info("Rôle USER créé: {}", role);
        }

        client.setRole(role);
        Client newClient = this.clientRepository.save(client);
        logger.info("Client créé avec succès: {}", newClient);

        Validation validation = validationService.create(newClient);
        logger.info("Validation créée pour le client: {}", validation);

        return newClient;
    }

    public Client validate(int code) {
        logger.info("Validation du client avec le code: {}", code);
        Validation validation = validationService.readByCode(code);

        if (validation == null) {
            logger.warn("Échec de la validation: Code invalide ou inexistant.");
            return null;
        }

        validation.getClient().setActive(true);
        Client validatedClient = this.clientRepository.save(validation.getClient());
        logger.info("Client validé avec succès: {}", validatedClient);

        return validatedClient;
    }

    // Read
    public List<Client> read() {
        logger.info("Récupération de tous les clients.");
        List<Client> clients = this.clientRepository.findAll();
        logger.info("Nombre total de clients récupérés: {}", clients.size());
        return clients;
    }

    public Client readById(int id) {
        logger.info("Recherche d'un client avec l'ID: {}", id);
        Optional<Client> optionalClient = this.clientRepository.findById(id);

        if (optionalClient.isPresent()) {
            logger.info("Client trouvé: {}", optionalClient.get());
            return optionalClient.get();
        }

        logger.warn("Aucun client trouvé avec l'ID: {}", id);
        return null;
    }

    // Update
    public Client update(int id, Client client) {
        logger.info("Mise à jour du client avec l'ID: {}", id);
        Client dbClient = this.readById(id);

        if (dbClient != null) {
            dbClient.setAge(client.getAge());
            dbClient.setNom(client.getNom());
            dbClient.setDate_naissance(client.getDate_naissance());
            dbClient.setPrenom(client.getPrenom());
            dbClient.setSexe(client.getSexe());
            dbClient.setDate_adhesion(client.getDate_adhesion());
            dbClient.setEmail(client.getEmail());
            dbClient.setActive(client.isActive());

            Client updatedClient = this.clientRepository.save(dbClient);
            logger.info("Client mis à jour avec succès: {}", updatedClient);
            return updatedClient;
        }

        logger.warn("Échec de la mise à jour: Aucun client trouvé avec l'ID: {}", id);
        return null;
    }

    // Delete
    public void delete(int id) {
        logger.info("Suppression du client avec l'ID: {}", id);

        if (clientRepository.existsById(id)) {
            this.clientRepository.deleteById(id);
            logger.info("Client supprimé avec succès.");
        } else {
            logger.warn("Échec de la suppression: Aucun client trouvé avec l'ID: {}", id);
        }
    }
}
