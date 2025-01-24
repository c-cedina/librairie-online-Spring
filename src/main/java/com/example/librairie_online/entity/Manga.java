package com.example.librairie_online.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nserie;
    private String nom;
    @Column(name = "date_parution", nullable = false)
    private int dateParution;
    private int tome;
    private int nbExemplaire;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "nomMangaka", referencedColumnName = "nom")
    @JoinColumn(name = "prenomMangaka", referencedColumnName = "prenom")
    private Mangaka mangaka;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "nomFournisseur")
    private Fournisseur fournisseur;

}
