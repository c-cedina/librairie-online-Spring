package com.example.librairie_online.entity;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Mangaka")
public class Mangaka {

    @EmbeddedId
    private MangakaId id;
    private String sexe;
    private String nationalite;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class MangakaId implements Serializable {
        private String nom;
        private String prenom;

        @Override
        public boolean equals(Object other) {
            if (this == other)
                return true;
            if (other == null || getClass() != other.getClass())
                return false;
            MangakaId mangakaId = (MangakaId) other;
            return Objects.equals(nom, mangakaId.nom) &&
                    Objects.equals(prenom, mangakaId.prenom);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nom, prenom);
        }
    }
}
