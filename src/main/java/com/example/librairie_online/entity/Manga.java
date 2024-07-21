package com.example.librairie_online.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Manga {
    @Id
    private int nserie;
    private String nom;
    private int date_parution;
    private int tome;
    private String nbExemplaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nomMangaka", referencedColumnName = "nom")
    @JoinColumn(name = "prenomMangaka", referencedColumnName = "prenom")
    private Mangaka mangaka;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nomFournisseur")
    private Fournisseur fournisseur;

}
