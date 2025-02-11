# Librairie de Manga - Développement avec Java Spring Boot

![](backend.webp)

## Description du Projet

Cette application est une librairie en ligne de mangas développée en utilisant Java et Spring Boot. Elle permet de gérer une base de données MySQL pour effectuer des opérations CRUD (Créer, Lire, Mettre à jour, Supprimer) sur différentes entités telles que les clients, les mangas, les animes, etc. L'application est conteneurisée avec Docker pour faciliter le déploiement et l'exécution.

L'application utilise Spring Security pour sécuriser les endpoints avec des rôles et des JSON Web Tokens (JWT) pour l'authentification. Les rôles définis sont `USER` et `ADMIN`, et les JWT sont utilisés pour sécuriser les endpoints et gérer les sessions de manière stateless.

## Technologies Utilisées

- **JDK**: 22
- **Spring Boot**: 3.3.1
- **Hibernate ORM**: 6.5.2.Final
- **Base de données**: MySQL
- **Outils de Build**: Maven
- **Conteneurisation**: Docker



## Introduction


Cette application utilise une base de données MySQL connectée à une API Spring Boot pour gérer une librairie de mangas. L'objectif est de pouvoir créer, lire, modifier et supprimer des entités grâce à Spring Boot.

### Liens - Architecture du Projet

[Architecture ](Architecture.md)

[Architecture détaillée](Architecture-detaille.md)

## Prérequis

- **Java**: JDK 22
- **Maven**
- **Docker** et **Docker Compose**

## Installation

1. **Cloner le dépôt**:
    ```sh
    git clone <URL_DU_DEPOT>
    cd librairie-online
    ```

2. **Configurer la base de données**:
    - Assurez-vous que Docker est installé et en cours d'exécution.
    - Lancer les services Docker:
        ```sh
        docker-compose up -d
        ```

3. **Configurer les propriétés de l'application**:
    - Les propriétés de l'application sont définies dans `src/main/resources/application.properties` et `src/test/resources/application-test.properties`.

4. **Construire le projet**:
    ```sh
    mvn clean install
    ```

5. **Lancer l'application**:
    ```sh
    mvn spring-boot:run
    ```
## Gestion des Requêtes API - Librairie de Manga  

L’API expose plusieurs **endpoints** permettant d'ajouter, modifier, lire et supprimer des ressources dans la base de données.  

- **Toutes les requêtes POST, PUT et DELETE nécessitent un token JWT valide** pour être exécutées.  
- Pour **supprimer** une ressource, il faut ajouter son **ID dans l’URL** lors de la requête DELETE.  
- La requête **PUT (update)** utilise le **même URL que POST**, mais nécessite également **l’ID dans l’URL** et un **body identique** avec les nouvelles valeurs.  
- La requête **GET (lecture)** est accessible **sans authentification** et permet de récupérer les données enregistrées.  
- **Ci-dessous, toutes les requêtes POST disponibles dans l'API sont listées.**  

### Client

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

### Manga
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
### Anime
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
### Role
To create a Role, send a POST request to /Role with the following JSON:
```json
{
    "role": "USER"
}
```
### Achete
To create an `Achete`, send a POST request to `/Achete` with the following JSON:
```json
{
    "client": {"nadherent": 123},
    "manga": {"nserie": 93497},
    "date": "2023-01-01",
    "prix": 19.99
}
```
### Loue
To create a `Loue`, send a POST request to `/Loue` with the following JSON:
```json
{
    "client": {"nadherent": 123},
    "anime": {"nserie": 93497},
    "date": "2023-01-01",
    "dateRetour": "2023-01-10"
}
```
### NoteM
To create a `NoteM`, send a POST request to `/Note/Manga` with the following JSON:
```json
{
    "nadherent": 123,
    "nserie": 93497,
    "valeur": 4.5,
    "date": "2023-01-01"
}
```
### NoteA
To create a `NoteA`, send a POST request to `/Note/Anime` with the following JSON:
```json
{
    "nadherent": 123,
    "nserie": 93497,
    "valeur": 4.5,
    "date": "2023-01-01"
}
```
### Classe
To create a `Classe`, send a POST request to `/Classe` with the following JSON:
```json
{
    "nserie": 93497,
    "type": "Action"
}
```
### Fournisseur
To create a `Fournisseur`, send a POST request to `/Fournisseur` with the following JSON:
```json
{
    "nom": "Manga World",
    "ville": "Versailles"
}
```
### Mangaka
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

## Licence  

Ce projet est sous licence MIT. Vous êtes libre de l'utiliser et de le modifier.  
Cependant, **aucune contribution externe ne sera acceptée sur ce repository officiel**.  
Merci d'ouvrir une issue pour toute suggestion. 🚀 