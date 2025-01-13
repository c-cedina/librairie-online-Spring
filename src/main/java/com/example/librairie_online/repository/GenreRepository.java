package com.example.librairie_online.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.librairie_online.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, String> {

}
