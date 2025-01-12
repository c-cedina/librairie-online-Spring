package com.example.librairie_online.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Studio {
    @Id
    private String nom;
    private String pays;
    private String nomDirecteur;
    private String prenomDirecteur;

}
