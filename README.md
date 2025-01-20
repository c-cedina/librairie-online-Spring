# Application Crud Java Spring Boot Librairie de Manga

JDK 22
Spring boot 3.3.1
Hibernate ORM core version 6.5.2.Final

## Introduction

Utilisation d'une base de donnés MySql raccorder à cette api.
Objectif pouvoir créer, lire, modifier et supprimer grâce à Spring Boot


## Body en Json pour créer les Entités 

### Body attendu pour créer mangaka
```
{
  "id": {
    "nom": "exemple",
    "prenom": "exemple"
  },
  "sexe": "exemple",
  "nationalite": "exemple"
}
```
### Body attendu pour créer fournisseur
```
{
    "nom": "exemple",
    "ville" : "exemple"
}
```
### Body attendu pour créer Manga
```
{
        "nserie": 12378,
        "nom": "Dragon Ball",
        "date_parution": 1984,
        "tome": 5,
        "nbExemplaire": "20",
        "mangaka": {
            "id": {
                "nom": "Toriyama",
                "prenom": "Akira"
            },
            "sexe": "M",
            "nationalite": "Japonaise"
        },
        "fournisseur": {
            "nom": "Manga World",
            "ville": "Versailles"
        }
    }
```    

