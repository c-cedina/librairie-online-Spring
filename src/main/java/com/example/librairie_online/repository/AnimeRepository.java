package com.example.librairie_online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.librairie_online.entity.Anime;

public interface AnimeRepository extends JpaRepository<Anime, Integer> {

}
