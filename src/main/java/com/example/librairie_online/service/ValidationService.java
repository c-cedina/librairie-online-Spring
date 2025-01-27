package com.example.librairie_online.service;

import com.example.librairie_online.entity.Client;
import com.example.librairie_online.entity.Validation;
import com.example.librairie_online.repository.ValidationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;
@AllArgsConstructor
@Service
public class ValidationService {
    private  ValidationRepository validationRepository;
    private  NotificationServices notificationServices;



    public Validation create(Client client){
        Validation validation = new Validation();
        validation.setClient(client);
        validation.setCreation(Instant.now());
        validation.setExpire(Instant.now().plus(10,MINUTES));

        Random random = new Random();
        int code = random.nextInt(999999);
        validation.setCode(code);
        this.notificationServices.sendMail(validation);
        client.setActive(true);

        return this.validationRepository.save(validation);

    }


}
