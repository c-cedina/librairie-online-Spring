package com.example.librairie_online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.librairie_online.entity.Anime;
import com.example.librairie_online.entity.Manga;
import com.example.librairie_online.entity.Studio;

public interface AnimeRepository extends JpaRepository<Anime, Integer> {
    boolean existsByNomAndDateAndMangaAndStudio(String nom, int date, Manga manga, Studio studio);
}
