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
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@IdClass(Classe.ClasseId.class)
public class Classe {
    @Id
    private int nSerie;

    @Id
    private String type;

    @ManyToOne
    @JoinColumn(name = "nserie", insertable = false, updatable = false)
    private Manga manga;

    @ManyToOne
    @JoinColumn(name = "type", insertable = false, updatable = false)
    private Genre genre;

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class ClasseId implements Serializable {
        private int nSerie;
        private String type;

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            ClasseId classeId = (ClasseId) o;
            return nSerie == classeId.nSerie && Objects.equals(type, classeId.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nSerie, type);
        }
    }
}