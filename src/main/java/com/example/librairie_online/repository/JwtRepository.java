package com.example.librairie_online.repository;

import com.example.librairie_online.entity.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface JwtRepository extends JpaRepository<Jwt, Integer> {
    Optional<Jwt> findByToken(String token);

    @Query("SELECT j FROM Jwt j WHERE j.client.email = :email AND j.desactive = false")
    List<Jwt> findAllByEmail(@Param("email") String email);

}
