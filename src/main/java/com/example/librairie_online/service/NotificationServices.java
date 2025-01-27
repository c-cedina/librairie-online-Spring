package com.example.librairie_online.service;

import com.example.librairie_online.entity.Validation;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationServices {
    JavaMailSender javaMailSender;
    public void sendMail(Validation validation) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("Sender@exemple.com");
        mailMessage.setTo(validation.getClient().getEmail());
        ;String text = "Votre code de validation est : " + validation.getCode();
        mailMessage.setText(text);
        javaMailSender.send(mailMessage);
    }
}
