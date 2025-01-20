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
@IdClass(NoteM.NoteMId.class)
public class NoteM {
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
    private Manga manga;

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class NoteMId implements Serializable {
        private int nadherent;
        private int nSerie;

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            NoteMId noteMId = (NoteMId) o;
            return nadherent == noteMId.nadherent && nSerie == noteMId.nSerie;
        }

        @Override
        public int hashCode() {
            return Objects.hash(nadherent, nSerie);
        }
    }
}