# Application Crud Java Spring Boot Librairie de Manga


## Introduction

Utilisation d'une base de donnés MySql raccorder a cette api 
objectif pouvoir créer, lire, modifier et supprimer grace a Spring Boot


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

