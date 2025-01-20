package com.example.librairie_online.entity;

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
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nSerie;

    private String nom;

    private int date;

    @ManyToOne
    @JoinColumn(name = "nSerieM")
    private Manga manga;

    @ManyToOne
    @JoinColumn(name = "nomStudio")
    private Studio studio;
}