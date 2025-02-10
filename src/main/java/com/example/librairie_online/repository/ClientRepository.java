package com.example.librairie_online.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.librairie_online.entity.Client;

import java.util.Optional;


public interface ClientRepository extends JpaRepository<Client, Integer> {

    boolean existsByEmail(String email);

    Optional<Client> findByEmail(String username);
}
