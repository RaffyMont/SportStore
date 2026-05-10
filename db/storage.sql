/* Controllare gli ON CASCADE ecc... */

CREATE TABLE Utente(
	id_utente char(6) NOT NULL,
    nome varchar(128) NOT NULL,
    cognome varchar(128) NOT NULL,
    email varchar(256) NOT NULL,
    pwd varchar(128) NOT NULL,
    ruolo enum('admin', 'common', 'guest'),
    cellulare char(10),
    
    PRIMARY KEY(id_utente)
);

CREATE TABLE Prodotto(
	id_prodotto char(12) NOT NULL,
    modello varchar(64),
    descrizione varchar(256),
    prezzo decimal(10, 2) NOT NULL,
    marca varchar(64),
    genere enum('uomo', 'donna', 'bambino', 'unisex') NOT NULL,
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
	
    
CREATE TABLE Indirizzo(
	città varchar(128) NOT NULL,
    CAP char(5) NOT NULL,
    via varchar(128) NOT NULL,
    civico varchar(5) NOT NULL,
    id_utente char(6),
    
    PRIMARY KEY(città, CAP, via, civico),
    FOREIGN KEY(id_utente) REFERENCES Utente(id_utente) ON UPDATE cascade ON DELETE set null
);

CREATE TABLE Ordine(
	id_ordine char(12) NOT NULL,
    data_ordine datetime,
    stato_ordine enum('consegnato', 'in spedizione', 'annullato'),
    prezzo_totale decimal(10, 2) NOT NULL,
    id_utente char(6),
    città_spedizione varchar(128),
    CAP_spedizione char(5),
    via_spedizione varchar(128),
    civico_spedizione varchar(5),
    
    PRIMARY KEY(id_ordine),
    FOREIGN KEY(citta_spedizione, CAP_spedizione, via_spedizione, civico_spedizione) REFERENCES Indirizzo(città, CAP, via, civico) ON UPDATE cascade ON DELETE set null
);

CREATE TABLE DettagliOrdine(
	id_dettaglio char(8) NOT NULL,
    quantità int NOT NULL,
    prezzo_unitario decimal(10, 2) NOT NULL,
    id_ordine char(12),
    id_prodotto char(12),
    
    PRIMARY KEY(id_dettaglio),
    FOREIGN KEY(id_ordine) REFERENCES Ordine(id_ordine) ON UPDATE cascade ON DELETE set null,
    FOREIGN KEY(id_prodotto) REFERENCES Prodotto(id_prodotto) ON UPDATE cascade ON DELETE set null
);

CREATE TABLE SupportoColore(
	id_prodotto char(12),
    nome varchar(32),
    
    PRIMARY KEY(id_prodotto, nome),
    FOREIGN KEY(id_prodotto) REFERENCES Prodotto(id_prodotto) ON UPDATE cascade ON DELETE set null,
    FOREIGN KEY(nome) REFERENCES Colore(nome) ON UPDATE cascade ON DELETE set null
);

CREATE TABLE SupportoTaglia(
	id_prodotto char(12),
    taglia varchar(10),
    
    PRIMARY KEY(id_prodotto, taglia),
    FOREIGN KEY(id_prodotto) REFERENCES Prodotto(id_prodotto) ON UPDATE cascade ON DELETE set null,
    FOREIGN KEY(taglia) REFERENCES Taglia(taglia) ON UPDATE cascade ON DELETE set null
);

/* controllare su eclipse la soluzione con immagini nel file system */
CREATE TABLE Immagine(
	pathname varchar(255) /* Controllare che campo è su eclipse */,
    
    PRIMARY KEY(pathname)
);
