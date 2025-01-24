package com.example.librairie_online.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.librairie_online.entity.Manga;

public interface MangaRepository extends JpaRepository<Manga, Integer> {

    boolean existsByNomAndDateParutionAndTome(String nom, int dateParution, int tome);
}
