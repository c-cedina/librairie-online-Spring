package com.example.librairie_online.repository;

import com.example.librairie_online.entity.Validation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidationRepository extends JpaRepository<Validation,Integer> {
    Validation findByCode(int code);
}
