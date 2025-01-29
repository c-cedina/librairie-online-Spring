package com.example.librairie_online.service;

import com.example.librairie_online.entity.Validation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationServices {
    private static final Logger logger = LoggerFactory.getLogger(NotificationServices.class);

    private JavaMailSender javaMailSender;

    public void sendMail(Validation validation) {
        logger.info("Préparation de l'e-mail pour {}", validation.getClient().getEmail());

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("Sender@exemple.com");
            mailMessage.setTo(validation.getClient().getEmail());

            String text = "Votre code de validation est : " + validation.getCode();
            mailMessage.setText(text);

            javaMailSender.send(mailMessage);
            logger.info("E-mail envoyé avec succès à {}", validation.getClient().getEmail());

        } catch (MailException e) {
            logger.error("Échec de l'envoi de l'e-mail à {}: {}", validation.getClient().getEmail(), e.getMessage());
        }
    }
}
