package com.example.librairie_online.service;

import com.example.librairie_online.entity.Client;
import com.example.librairie_online.entity.Validation;
import com.example.librairie_online.repository.ValidationRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

@AllArgsConstructor
@Service
public class ValidationService {
    private static final Logger logger = LoggerFactory.getLogger(ValidationService.class);

    private ValidationRepository validationRepository;
    private NotificationServices notificationServices;

    public Validation create(Client client) {
        logger.info("Création d'une nouvelle validation pour le client: {}", client.getEmail());

        Validation validation = new Validation();
        validation.setClient(client);
        validation.setCreation(Instant.now());
        validation.setExpire(Instant.now().plus(10, MINUTES));

        Random random = new Random();
        int code = random.nextInt(999999);
        validation.setCode(code);

        try {
            this.notificationServices.sendMail(validation);
            logger.info("E-mail de validation envoyé avec succès à {}", client.getEmail());
        } catch (Exception e) {
            logger.error("Échec de l'envoi de l'email à {}: {}", client.getEmail(), e.getMessage());
        }

        Validation savedValidation = this.validationRepository.save(validation);
        logger.info("Validation enregistrée avec succès: {}", savedValidation);
        return savedValidation;
    }

    public Validation readByCode(int code) {
        logger.info("Recherche d'une validation avec le code: {}", code);
        Validation validation = this.validationRepository.findByCode(code);

        if (validation == null) {
            logger.warn("Aucune validation trouvée pour le code: {}", code);
            return null;
        }

        logger.info("Validation trouvée: {}", validation);
        return validation;
    }
}
