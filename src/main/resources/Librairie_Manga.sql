#PROJET INFO

create database Librairie_Manga ;

use Librairie_Manga ;

create table Mangaka (
	Nom varchar (255) not null,
    Prenom varchar (255) not null,
    Sexe varchar (255) not null, 
    Nationalite varchar (255) not null,
    primary key (Nom, prenom)
    );

create table Fournisseur (
	Nom varchar (255) not null, 
    Ville varchar (255) not null,
    primary key (Nom)
);

create table Manga (
	NSerie int not null, 
    Nom varchar (255) not null, 
    Date_parution int not null,
    Tome int not null,
    NBExemplaire int not null, 
    NomMangaka varchar (255), 
    PrenomMangaka varchar (255), 
    NomFournisseur varchar (255),
    primary key (NSerie),
    foreign key (NomMangaka, PrenomMangaka) references Mangaka(Nom, Prenom),
    foreign key (NomFournisseur) references Fournisseur(Nom)
);

create table Client (
	NAdherent int not null, 
    Nom varchar (255), 
    Prenom varchar (255), 
    Sexe varchar(30), 
    Age int, 
    Date_Naissance date, 
    Date_Adhesion date,
    primary key (Nadherent)
);

create table Studio (
	Nom varchar (255),
    Pays varchar (255),
    nomDirecteur varchar (255),
    PrenomDirecteur varchar (255),
    primary key (nom) 
);

create table Anime (
	NSerie int not null, 
    Nom varchar (255) not null, 
    Date int not null,
    NSerieM int, 
    NomStudio varchar (255),
    primary key (NSerie),
    foreign key (NSerieM) references Manga(Nserie), 
	foreign key (NomStudio) references Studio(Nom)
);

create table Genre (
	Type varchar (255),
    primary key (Type) 
);

create table Classe (
	NSerie int not null, 
    Type varchar (255),
    primary key (NSerie, Type),
    foreign key (NSerie) references Manga(NSerie),
    foreign key (Type) references Genre(type)
);

 
create table Achete ( 
    Id int AUTO_INCREMENT, 
	Nadherent int not null,
	NSerie int not null, 
    Date date,
    Prix decimal(9,2),
    primary key (Id),
    foreign key (NSerie) references Manga(NSerie),
	foreign key (NAdherent) references Client (NAdherent)
);

create table Loue (
    Id int AUTO_INCREMENT, 
	NAdherent int, 
    NSerie int,
    Date date, 
    DateRetour date,
    DateMaximale date ,
    Prix decimal(9,2),
    primary key (Id),
    foreign key (NAdherent) references Client (NAdherent),
    foreign key (NSerie) references Anime (NSerie)
);

create table NoteM (
	Nadherent int, 
    NSerie int, 
    Date date, 
    Valeur decimal(9,2), 
    primary key (NAdherent, NSerie), 
    foreign key (NAdherent) references Client(NAdherent),
    foreign key (NSerie) references Manga(NSerie)
);

create table NoteA (
	Nadherent int, 
    NSerie int, 
    Date date, 
    Valeur decimal(9,2), 
    primary key (Nadherent, NSerie), 
    foreign key (NAdherent) references Client(NAdherent),
    foreign key (NSerie) references Anime(NSerie)
);


INSERT INTO Mangaka (Nom, Prenom, Sexe, Nationalite)
value
('Gosho', 'Aoyama', 'M', 'Japonaise'),
('Coelho', 'Cedina', 'M', 'Française'),
('Kentaro', 'Miura', 'M', 'Japonaise'),
('Toriyama', 'Akira', 'M', 'Japonaise'),
('Riyoko', 'Ikeda', 'M', 'Japonaise'),
('Kishimoto', 'Masashi', 'M', 'Japonaise'),
('Obata', 'Takeshi', 'M', 'Japonaise'),
('Bouaoula', 'Emilie Rabia', 'F', 'Française'),
('Tadatoshi', 'Fujimaki', 'M', 'Japonaise')
;


INSERT INTO Fournisseur (Nom, ville)
value
('Manga World', 'Versailles')
;


INSERT INTO Manga (NSerie, Nom ,Date_parution, Tome, NBExemplaire, NomMangaka, PrenomMangaka, NomFournisseur)
 value 
(55999, 'Detective Conan', 1994, 1, 10, 'Gosho', 'Aoyama', 'Manga World'),
(49950, 'Detective Conan', 1995, 2, 11, 'Gosho', 'Aoyama', 'Manga World'),
(31562, 'Berserk', 1989, 1, 15, 'Coelho', 'Cedina', 'Manga World'),
(82304, 'Berserk', 1990, 2, 8, 'Kentaro', 'Miura', 'Manga World'),
(63701, 'Berserk', 1991, 3, 8, 'Kentaro', 'Miura', 'Manga World'),
(12378, 'Dragon Ball', 1984, 5, 20, 'Toriyama', 'Akira', 'Manga World'),
(74858, 'La rose de Versailles', 1973, 1, 40, 'Riyoko', 'Ikeda', 'Manga World'),
(29600, 'Naruto', 1999, 1, 1, 'Kishimoto', 'Masashi', 'Manga World'),
(22256, 'Naruto', 1999, 2, 3, 'Kishimoto', 'Masashi', 'Manga World'),
(56947, 'DeathNote', 2003, 4, 15, 'Obata', 'Takeshi', 'Manga World'),
(93497, 'Shingeki no Kyojin', 2009, 1, 6,'Bouaoula' ,'Emilie Rabia', 'Manga World'),
(87232, 'Koroko no Basket', 2008, 1, 30, 'Tadatoshi', 'Fujimaki', 'Manga World' )
;

INSERT INTO  Client (Nadherent, Nom, Prenom, Sexe, Date_Naissance, Age, Date_Adhesion)
value 

(29453, 'Dubois', 'Léo', 'M', 20030308, 18, 20200506),
(23725, 'Bouaoula', 'Ahmed', 'M', 20140914, 17, 20200411),
(59132, 'Shogbeni', 'Guetina', 'F', 20060828, 15, 20210422),
(46610, 'Pierre', 'Alain', 'M', 19900102, 31, 20200519),
(36319, 'Robert', 'Samuel', 'M', 19870227, 34, 20210721),
(91781, 'Ada', 'Lina', 'F', 20030517, 18, 20201111),
(67569, 'Berger', 'Sarah', 'F', 20020601, 19, 20200929),
(14222, 'Petit', 'Eve', 'F', 20001211, 20, 20200512),
(17948, 'Fernandes', 'Gabriel', 'M', 20010725, 20, 20201102),
(61720, 'Leroy', 'Jade', 'F', 20070903, 14, 20211115),
(40285, 'Martin', 'Philippe', 'M', 19950407, 26, 20210706),
(74967, 'Ali', 'Yasin', 'M', 20071105, 14, 20200624),
(83128, 'Dupont', 'Emeline', 'F', 20080513, 13, 20210430),
(18351, 'Ledent', 'Andrea', 'F', 20001006, 21, 20210707)
;

insert into Studio (Nom, Pays, NomDirecteur, prenomDirecteur)
value
('Toei Animation Company', 'Japon', 'Shogbeni', 'Guetina'),
('Mappa', 'Japon', 'Bouaoula', 'Ahmed'),
('Studio Pierrot', 'Japon', 'Shogbeni', 'Guetina'),
('Bones', 'Japon', 'Bouaoula', 'Ahmed')
;

insert into Anime (NSerie, Nom, Date, NSerieM, NomStudio)
value
(58021, 'Lady Oscar', 1979, 74858, 'Studio Pierrot'),
(16649, 'Detective Conan', 1998, 49950, 'Toei Animation Company'),
(79545, 'Berserk', 1995, 31562, 'Bones'),
(95053, 'Berserk', 1996, 82304, 'Bones'),
(23277, 'Dragon Ball Z', 1985, 12378, 'Toei Animation Company'),
(10516, 'Naruto', 2000, 29600, 'Mappa'),
(21844, 'Naruto', 2000, 29600, 'Bones'),
(30382, 'Naruto Shippuden', 2001, 22256, 'Mappa'),
(66281, 'Death Note', 2006, 56947, 'Toei Animation Company'),
(44160, 'Shingeki no Kyojin', 2013, 93497, 'Mappa'),
(81849, 'Koroko no Basket', 2012, 87232, 'Bones');


insert into Achete (Nadherent, NSerie, Date, Prix)
value
(29453, 87232, 20211105, 6.85),
(17948, 82304, 20211105, 6.85),
(40285, 12378, 20211107, 6.85),
(46610, 87232, 20211108, 6.85),
(18351, 56947, 20211110, 6.85),
(91781, 55999, 20211110, 6.85),
(91781, 49950, 20211110, 6.85),
(14222, 74858, 20211111, 6.85),
(23725, 93497, 20211116, 6.85),
(29453, 29600, 20211120, 6.85),
(29453, 22256, 20211120, 6.85),
(18351, 93497, 20211122, 6.85),
(40285, 93497, 20211123, 6.85),
(36319, 29600, 20211128, 6.85),
(36319, 22256, 20211130, 6.85);



insert into Loue (Nadherent, NSerie, Date, DateRetour, DateMaximale, prix)
value
(67569, 44160, 20211103, 20211111, 20211117, 1.15),
(74967, 81849, 20211104, 20211118, 20211118, 1.15),
(83128, 66281, 20211104, 20211106, 20211118, 1.15),
(83128, 16649, 20211104, 20211109, 20211118, 1.15),
(61720, 66281, 20211107, 20211111, 20211121, 1.15),
(59132, 79545, 20211108, 20211120, 20211122, 1.15),
(59132, 30382, 20211108, 20211115, 20211122, 1.15),
(46610, 66281, 20211112, 20211118, 20211126, 1.15),
(36319, 23277, 20211115, 20211129, 20211129, 1.15),
(67569, 66281, 20211122, 20211125, 20211205, 1.15),
(36319, 58021, 20211123, 20211126, 20211206, 1.15),
(14222, 58021, 20211127, 20211130, 20211210, 1.15),
(14222, 79545, 20211128, 20211206, 20211211, 1.15);


insert into NoteM (Nadherent, NSerie, Date, Valeur)
value 
(91781, 49950, 20211101, 5),
(36319, 49950, 20211103, 4),
(61720, 93497, 20211104, 5),
(29453, 82304, 20211104, 4),
(29453, 12378, 20211106, 4.5),
(74967, 74858, 20211110, 2),
(18351, 93497, 20211111, 4),
(14222, 29600, 20211114, 5),
(59132, 56947, 20211115, 4),
(23725, 56947, 20211123, 4),
(67569, 29600, 20211126, 4.5),
(91781, 31562, 20211127, 3),
(83128, 12378, 20211130, 1)
;


insert into  NoteA (Nadherent, NSerie, Date, Valeur)
value
(17948, 81849, 20211102, 5),
(40285, 81849, 20211102, 5),
(67569, 58021, 20211102, 5),
(17948, 44160, 20211106, 5),
(14222, 23277, 20211107, 3),
(46610, 79545, 20211111, 2),
(36319, 10516, 20211113, 4),
(61720, 30382, 20211113, 4),
(91781, 30382, 20211117, 5),
(59132, 58021, 20211120, 3),
(23725, 66281, 20211122, 4),
(74967, 16649, 20211125, 5),
(40285, 16649, 20211129, 3)
;
insert into Genre (Type)
value 
('Aventure'),
('Sport'),
('Fantastique'),
('Tragique'),
('Comique'),
('Amour'),
('Policier'),
('Horreur')
;

insert into Classe (NSerie, Type)
value
(55999, 'Policier'),
(49950, 'Policier'),
(31562, 'Aventure'),
(31562, 'Fantastique'),
(82304, 'Aventure'),
(82304, 'Fantastique'),
(63701, 'Aventure'),
(63701, 'Fantastique'),
(12378, 'Aventure'),
(12378, 'Comique'),
(74858, 'Aventure'),
(29600, 'Aventure'),
(29600, 'Comique'),
(22256, 'Aventure'),
(22256, 'Comique'),
(56947, 'Policier'),
(56947, 'Horreur'),
(93497, 'Aventure'),
(93497, 'Horreur'),
(87232, 'Sport')
;
 
