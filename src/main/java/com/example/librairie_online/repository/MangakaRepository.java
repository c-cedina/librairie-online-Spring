package com.example.librairie_online.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.librairie_online.entity.Mangaka;
import com.example.librairie_online.entity.Mangaka.MangakaId;

public interface MangakaRepository extends JpaRepository<Mangaka, MangakaId> {

}
