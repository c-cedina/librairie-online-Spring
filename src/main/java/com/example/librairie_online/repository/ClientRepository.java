package com.example.librairie_online.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.librairie_online.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

}