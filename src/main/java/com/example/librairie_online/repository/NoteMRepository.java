package com.example.librairie_online.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.librairie_online.entity.NoteM;
import com.example.librairie_online.entity.NoteM.NoteMId;

public interface NoteMRepository extends JpaRepository<NoteM, NoteMId> {

}
