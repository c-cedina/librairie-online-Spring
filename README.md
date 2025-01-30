# Application Crud Java Spring Boot Librairie de Manga

JDK 22
Spring boot 3.3.1
Hibernate ORM core version 6.5.2.Final

## Introduction

Utilisation d'une base de donnés MySql raccorder à cette api.
Objectif pouvoir créer, lire, modifier et supprimer grâce à Spring Boot

# Librairie Online API

This document explains how to create various entities in the Librairie Online API using JSON.

## Table of Contents
- [Client](#client)
- [Manga](#manga)
- [Anime](#anime)
- [Role](#role)
- [Achete](#achete)
- [Loue](#loue)
- [NoteM](#notem)
- [NoteA](#notea)
- [Classe](#classe)
- [Fournisseur](#fournisseur)
- [Mangaka](#mangaka)

## Client

To create a `Client`, send a POST request to `/Client` with the following JSON:

```json
{
    "nom": "Doe",
    "prenom": "John",
    "sexe": "M",
    "age": 30,
    "date_naissance": "1991-01-01",
    "date_adhesion": "2021-01-01",
    "email": "Doe.John@exemple.com",
    "password": "password"
}
```

## Manga
To create a `Manga`, send a POST request to `/Manga` with the following JSON:
```json
{
    "nserie": 93497,
    "nom": "Naruto",
    "dateParution": 2009,
    "tome": 1,
    "nbExemplaire": 6,
    "mangaka": {
        "id": {
            "nom": "lou",
            "prenom": "rock"
        },
        "sexe": "F",
        "nationalite": "Française"
        },
    "fournisseur": {
        "nom": "Manga World",
        "ville": "Versailles"
        }
}
```
## Anime
To create an `Anime`, send a POST request to `/Anime` with the following JSON:
```json
{
    "nom": "Naruto",
    "date": 2002,
    "manga": {
        "nserie": 93497
    },
    "studio": {
        "nom": "Studio Pierrot"
    }
}
```
## Role
To create a Role, send a POST request to /Role with the following JSON:
```json
{
    "role": "USER"
}
```
## Achete
To create an `Achete`, send a POST request to `/Achete` with the following JSON:
```json
{
    "client": {"nadherent": 123},
    "manga": {"nserie": 93497},
    "date": "2023-01-01",
    "prix": 19.99
}
```
## Loue
To create a `Loue`, send a POST request to `/Loue` with the following JSON:
```json
{
    "client": {"nadherent": 123},
    "anime": {"nserie": 93497},
    "date": "2023-01-01",
    "dateRetour": "2023-01-10"
}
```
## NoteM
To create a `NoteM`, send a POST request to `/Note/Manga` with the following JSON:
```json
{
    "nadherent": 123,
    "nserie": 93497,
    "valeur": 4.5,
    "date": "2023-01-01"
}
```
## NoteA
To create a `NoteA`, send a POST request to `/Note/Anime` with the following JSON:
```json
{
    "nadherent": 123,
    "nserie": 93497,
    "valeur": 4.5,
    "date": "2023-01-01"
}
```
## Classe
To create a `Classe`, send a POST request to `/Classe` with the following JSON:
```json
{
    "nserie": 93497,
    "type": "Action"
}
```
## Fournisseur
To create a `Fournisseur`, send a POST request to `/Fournisseur` with the following JSON:
```json
{
    "nom": "Manga World",
    "ville": "Versailles"
}
```
## Mangaka
To create a `Mangaka`, send a POST request to `/Mangaka` with the following JSON:
```json
{
    "id": {
        "nom": "Test",
        "prenom": "Mangaka"
    },
    "sexe": "M",
    "nationalite": "Japonaise"
}
```