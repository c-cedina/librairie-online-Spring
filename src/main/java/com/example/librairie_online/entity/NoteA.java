package com.example.librairie_online.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@IdClass(NoteA.NoteAId.class)
public class NoteA {
    @Id
    private int nadherent;

    @Id
    private int nSerie;

    private LocalDate date;
    private double valeur;

    @ManyToOne
    @JoinColumn(name = "nadherent", insertable = false, updatable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "nSerie", insertable = false, updatable = false)
    private Anime anime;

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class NoteAId implements Serializable {
        private int nadherent;
        private int nSerie;

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            NoteAId noteAId = (NoteAId) o;
            return nadherent == noteAId.nadherent && nSerie == noteAId.nSerie;
        }

        @Override
        public int hashCode() {
            return Objects.hash(nadherent, nSerie);
        }
    }
}