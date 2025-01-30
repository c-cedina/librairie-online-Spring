package com.example.librairie_online.dto;

import com.example.librairie_online.entity.Client;
import com.example.librairie_online.entity.Manga;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class AcheteDTO {

    @NotNull(message = "Le client ne peut pas être null. Exemple attendu: { \"client\": { \"nadherent\": 123 } }")
    private Client client;
    @NotNull(message = "Le manga ne peut pas être null. Exemple attendu: { \"manga\": { \"nserie    \": 123 } }")
    private Manga manga;


    @NotNull(message = "La date ne peut pas être null")
    private LocalDate date;

    @NotNull(message = "Le prix ne peut pas être null")
    private BigDecimal prix;
}
