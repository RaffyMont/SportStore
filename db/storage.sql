DROP DATABASE IF EXISTS storage;
CREATE DATABASE storage;
USE storage;

CREATE TABLE Indirizzo(
	id_indirizzo int AUTO_INCREMENT,
    provincia varchar(128),
    stato varchar(128),
	citta varchar(128) NOT NULL,
    CAP char(5) NOT NULL,
    via varchar(128) NOT NULL,
    civico varchar(5) NOT NULL,
    
    PRIMARY KEY(id_indirizzo)
);

CREATE TABLE Utente(
	id_utente char(6) NOT NULL,
    nome varchar(128) NOT NULL,
    cognome varchar(128) NOT NULL,
    email varchar(256) NOT NULL,
    pwd varchar(128) NOT NULL,
    ruolo enum('admin', 'common'),
    cellulare char(10),
    id_indirizzo int NOT NULL,
    
    PRIMARY KEY(id_utente),
    FOREIGN KEY(id_indirizzo) REFERENCES Indirizzo(id_indirizzo) ON UPDATE  cascade ON DELETE cascade
);

CREATE TABLE Prodotto(
	id_prodotto char(12) NOT NULL,
    modello varchar(64),
    descrizione varchar(256),
    prezzo decimal(10, 2) DEFAULT 0.0,
    attivo boolean DEFAULT TRUE,
    marca varchar(64),
    categoria enum('vestito', 'scarpa', 'accessorio') NOT NULL,
    genere enum('uomo', 'donna', 'bambino', 'unisex') DEFAULT 'unisex',
    stock int NOT NULL DEFAULT 0,
    
    PRIMARY KEY(id_prodotto)
);

CREATE TABLE Colore(
	nome varchar(32) NOT NULL,
    
    PRIMARY KEY(nome)
);

CREATE TABLE Taglia(
	taglia varchar(10) NOT NULL,
    
    PRIMARY KEY(taglia)
);
	
CREATE TABLE Ordine(
	id_ordine char(12) NOT NULL,
    data_ordine datetime,
    stato_ordine enum('consegnato', 'in_spedizione', 'annullato', 'in_preparazione'),
    prezzo_totale decimal(10, 2) DEFAULT 0.0,
    id_utente char(6) NOT NULL,
    id_indirizzo int NOT NULL,
    
    PRIMARY KEY(id_ordine),
    FOREIGN KEY(id_indirizzo) REFERENCES Indirizzo(id_indirizzo) ON UPDATE cascade ON DELETE cascade,
	FOREIGN KEY(id_utente) REFERENCES Utente(id_utente) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE DettagliOrdine(
    quantità int DEFAULT 0,
    prezzo_unitario decimal(10, 2) DEFAULT 0.0,
    id_ordine char(12) NOT NULL,
    id_prodotto char(12) NOT NULL,
    
    PRIMARY KEY(id_ordine, id_prodotto),
    FOREIGN KEY(id_ordine) REFERENCES Ordine(id_ordine) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(id_prodotto) REFERENCES Prodotto(id_prodotto) ON UPDATE cascade ON DELETE restrict
);

CREATE TABLE SupportoColore(
	id_prodotto char(12) NOT NULL,
    nome varchar(32) NOT NULL,
    
    PRIMARY KEY(id_prodotto, nome),
    FOREIGN KEY(id_prodotto) REFERENCES Prodotto(id_prodotto) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(nome) REFERENCES Colore(nome) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE SupportoTaglia(
	id_prodotto char(12) NOT NULL,
    taglia varchar(10) NOT NULL,
    
    PRIMARY KEY(id_prodotto, taglia),
    FOREIGN KEY(id_prodotto) REFERENCES Prodotto(id_prodotto) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(taglia) REFERENCES Taglia(taglia) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Immagine(
	pathname varchar(255) NOT NULL,
	mime_type VARCHAR(50) DEFAULT NULL,
    id_prodotto char(12) NOT NULL,

    PRIMARY KEY(pathname),

    FOREIGN KEY(id_prodotto) REFERENCES Prodotto(id_prodotto) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Scarpe(
	id_prodotto char(12) NOT NULL,
	id_scarpa int AUTO_INCREMENT,
    tipo_suola varchar(30),
    materiale varchar(128),
    
    PRIMARY KEY(id_prodotto, id_scarpa),
    FOREIGN KEY(id_prodotto) REFERENCES Prodotto(id_prodotto) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Vestiti(
	id_prodotto char(12) NOT NULL,
	id_vestito int AUTO_INCREMENT,
    tipo_vita varchar(30),
    tessuto varchar(128),
    stagione varchar(30),
    categoriaVestito enum('maglietta', 'pantalone', 'tuta') NOT NULL,
    tipo_collo varchar(30),
    manica enum('corta', 'lunga'),
    gamba enum('corti', 'lunghi'),
    
    PRIMARY KEY(id_prodotto, id_vestito),
    FOREIGN KEY(id_prodotto) REFERENCES Prodotto(id_prodotto) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Accessori(
	id_prodotto char(12) NOT NULL,
	id_accessorio int AUTO_INCREMENT,
    tipo_accessorio varchar(30),
    materiale varchar(128),
    
    PRIMARY KEY(id_prodotto, id_accessorio),
    FOREIGN KEY(id_prodotto) REFERENCES Prodotto(id_prodotto) ON UPDATE cascade ON DELETE cascade
);