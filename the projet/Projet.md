1. Frontend (Client)
Responsabilités :
Afficher les livres disponibles à l'achat ou à la location.
Permettre aux utilisateurs d'ajouter des livres au panier.
Permettre l'achat ou la location de livres.
Technologie : React (ou autre framework JS)
Interaction avec le Backend :
Consomme les API REST du backend pour afficher les livres, effectuer les achats et locations, et récupérer les détails des utilisateurs.
2. Frontend (Bibliothécaire)
Responsabilités :
Ajouter de nouveaux livres à la base de données.
Gérer l'inventaire des livres (ajouter, supprimer, modifier les stocks).
Gérer les commandes d'achat et les retours de livres.
Technologie : React (ou autre framework JS)
Interaction avec le Backend :
Utilise des API REST sécurisées pour gérer les stocks, livres, et commandes.
3. Backend (Spring Boot)
Responsabilités :
Fournir des API REST pour gérer les interactions entre le frontend et la base de données.
Gérer la logique métier (ajout de livres, gestion des achats, locations, retour des livres, etc.).
Authentifier et autoriser les utilisateurs (utilisateurs client et bibliothécaire).
Technologie : Spring Boot
Base de données : Utilisation d'une base de données SQL (par exemple MySQL ou PostgreSQL).
Gestion des rôles et permissions : Le backend vérifie les rôles des utilisateurs (client ou bibliothécaire) via une authentification JWT ou autre méthode sécurisée.
4. Base de données (SQL)
Tables principales :
Livre (Book) : Contient les informations des livres (titre, auteur, stock disponible, etc.).
Utilisateur (User) : Contient les informations des utilisateurs (nom, prénom, email, rôle, etc.).
Commande (Order) : Contient les informations sur les achats effectués (livre, utilisateur, date, prix).
Location (Rental) : Contient les informations sur les locations effectuées (livre, utilisateur, date de location, date de retour).
Historique de location (LoanHistory) : Suivi des dates de location et retour des livres.
Stock (Stock) : Quantité de livres disponibles pour chaque titre.
Relation entre les tables :
Livre et Commande/Location : Liens entre les livres et les commandes/locations.
Utilisateur et Commande/Location : Liens entre les utilisateurs et leurs commandes/locations.
Stock et Livre : Gère la quantité de livres disponibles dans l'inventaire.
Type de base de données : SQL (MySQL ou PostgreSQL)
5. Flux des données et interactions
Client Frontend :
Envoie des requêtes pour voir les livres, acheter ou louer des livres via des API REST.
Bibliothécaire Frontend :
Utilise des API REST pour gérer les livres, les stocks et les commandes.
Backend (Spring Boot) :
Traite les requêtes des deux frontends et manipule les données dans la base de données.
Gère l'authentification et l'autorisation des utilisateurs.
Base de données :
Stocke les informations des livres, utilisateurs, commandes, locations, etc.
