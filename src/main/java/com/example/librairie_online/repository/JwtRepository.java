package com.example.librairie_online.repository;

import com.example.librairie_online.entity.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtRepository extends JpaRepository<Jwt, Integer> {
}
