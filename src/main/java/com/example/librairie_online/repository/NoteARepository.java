package com.example.librairie_online.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.librairie_online.entity.NoteA;
import com.example.librairie_online.entity.NoteA.NoteAId;

public interface NoteARepository extends JpaRepository<NoteA, NoteAId> {

}
