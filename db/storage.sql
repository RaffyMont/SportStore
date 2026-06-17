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
    
    PRIMARY KEY(id_scarpa, id_prodotto),
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
    
    PRIMARY KEY(id_vestito, id_prodotto),
    FOREIGN KEY(id_prodotto) REFERENCES Prodotto(id_prodotto) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Accessori(
	id_prodotto char(12) NOT NULL,
	id_accessorio int AUTO_INCREMENT,
    tipo_accessorio varchar(30),
    materiale varchar(128),
    
    PRIMARY KEY(id_accessorio, id_prodotto),
    FOREIGN KEY(id_prodotto) REFERENCES Prodotto(id_prodotto) ON UPDATE cascade ON DELETE cascade
);

USE storage;

-- ==============================================
-- INDIRIZZI
-- ==============================================
INSERT INTO Indirizzo (provincia, stato, citta, CAP, via, civico) VALUES
('MI', 'Italia', 'Milano', '20121', 'Corso Buenos Aires', '12'),
('RM', 'Italia', 'Roma', '00186', 'Via del Corso', '45'),
('NA', 'Italia', 'Napoli', '80121', 'Via Toledo', '88'),
('TO', 'Italia', 'Torino', '10121', 'Via Roma', '5'),
('BO', 'Italia', 'Bologna', '40121', 'Via Indipendenza', '30'),
('FI', 'Italia', 'Firenze', '50123', 'Via de Tornabuoni', '20'),
('GE', 'Italia', 'Genova', '16121', 'Via XX Settembre', '17'),
('PA', 'Italia', 'Palermo', '90133', 'Via Maqueda', '60'),
('VE', 'Italia', 'Venezia', '30121', 'Via Garibaldi', '3'),
('BA', 'Italia', 'Bari', '70121', 'Via Sparano', '77');

-- ==============================================
-- UTENTI
-- ==============================================
INSERT INTO Utente (id_utente, nome, cognome, email, pwd, ruolo, cellulare, id_indirizzo) VALUES
('U00001', 'Admin', 'Sistema', 'admin@sportsstore.it', 'hashed_pwd_admin', 'admin', '3331234567', 1),
('U00002', 'Marco', 'Rossi', 'marco.rossi@email.it', 'hashed_pwd_001', 'common', '3389876543', 2),
('U00003', 'Giulia', 'Bianchi', 'giulia.bianchi@email.it', 'hashed_pwd_002', 'common', '3471122334', 3),
('U00004', 'Luca', 'Ferrari', 'luca.ferrari@email.it', 'hashed_pwd_003', 'common', '3206677889', 4),
('U00005', 'Sara', 'Conti', 'sara.conti@email.it', 'hashed_pwd_004', 'common', '3354455667', 5),
('U00006', 'Alessandro', 'Marino', 'alex.marino@email.it', 'hashed_pwd_005', 'common', '3489900112', 6),
('U00007', 'Valentina', 'Ricci', 'vale.ricci@email.it', 'hashed_pwd_006', 'common', '3312233445', 7),
('U00008', 'Davide', 'Lombardi', 'davide.lombardi@email.it', 'hashed_pwd_007', 'common', '3465566778', 8);

-- ==============================================
-- COLORI
-- ==============================================
INSERT INTO Colore (nome) VALUES
('Nero'),
('Bianco'),
('Grigio'),
('Rosso'),
('Blu'),
('Verde'),
('Giallo Fluo'),
('Arancione'),
('Navy'),
('Celeste');

-- ==============================================
-- TAGLIE
-- ==============================================
INSERT INTO Taglia (taglia) VALUES
('XS'),
('S'),
('M'),
('L'),
('XL'),
('XXL'),
('38'),
('39'),
('40'),
('41'),
('42'),
('43'),
('44'),
('45'),
('TU');

-- ==============================================
-- PRODOTTI
-- ==============================================

-- Scarpe (6 prodotti)
INSERT INTO Prodotto (id_prodotto, modello, descrizione, prezzo, attivo, marca, categoria, genere, stock) VALUES
('P000000001', 'AirRun Pro 3', 'Scarpa da running con ammortizzazione avanzata e suola in carbonio', 149.99, TRUE, 'Nike', 'scarpa', 'uomo', 80),
('P000000002', 'CloudFlex Ultra', 'Scarpa da trail running con tomaia impermeabile e grip estremo', 129.99, TRUE, 'Adidas', 'scarpa', 'donna', 60),
('P000000003', 'SpeedForce X', 'Scarpa da gara leggera per atletica su pista', 179.99, TRUE, 'Asics', 'scarpa', 'unisex', 45),
('P000000004', 'GymPower Elite', 'Scarpa da allenamento in palestra con supporto laterale rinforzato', 99.99, TRUE, 'Under Armour', 'scarpa', 'uomo', 100),
('P000000005', 'TennisPro Flash', 'Scarpa da tennis con suola in gomma per campi in terra rossa', 119.99, TRUE, 'New Balance', 'scarpa', 'donna', 55),
('P000000006', 'KidsRun Star', 'Scarpa da running per bambini con velcro e ammortizzazione soft', 59.99, TRUE, 'Nike', 'scarpa', 'bambino', 90),

-- Vestiti (8 prodotti)
('P000000007', 'DryFit Performance Tee', 'Maglietta tecnica a maniche corte con tessuto a dispersione del sudore', 34.99, TRUE, 'Nike', 'vestito', 'uomo', 200),
('P000000008', 'Compression Legging Pro', 'Pantalone compressivo da corsa con tasca laterale porta-smartphone', 64.99, TRUE, 'Adidas', 'vestito', 'donna', 150),
('P000000009', 'FlexTrack Jogger', 'Tuta da allenamento con zip laterale e tessuto quattro-way stretch', 89.99, TRUE, 'Puma', 'vestito', 'uomo', 75),
('P000000010', 'AeroMesh Tee', 'Maglietta a maniche corte in rete tecnica ultraleggera per allenamenti estivi', 29.99, TRUE, 'Under Armour', 'vestito', 'donna', 180),
('P000000011', 'ThermoRun Pant', 'Pantalone termico invernale per running all\'aperto con rivestimento interno in pile', 79.99, TRUE, 'Asics', 'vestito', 'uomo', 90),
('P000000012', 'KidsSport Tuta', 'Tuta sportiva per bambini, comoda e colorata, perfetta per la scuola e lo sport', 49.99, TRUE, 'Adidas', 'vestito', 'bambino', 120),
('P000000013', 'PowerLift Shorts', 'Pantalone corto da powerlifting con supporto lombare integrato', 54.99, TRUE, 'Reebok', 'vestito', 'uomo', 110),
('P000000014', 'YogaFlow Legging', 'Pantalone lungo da yoga in tessuto opaco ad alta elasticità', 59.99, TRUE, 'Nike', 'vestito', 'donna', 140),

-- Accessori (6 prodotti)
('P000000015', 'HydroRun Belt', 'Cintura porta-borraccia da running con tasche laterali impermeabili', 39.99, TRUE, 'Salomon', 'accessorio', 'unisex', 70),
('P000000016', 'ProGrip Gloves', 'Guanti da allenamento con imbottitura palmo e chiusura a velcro', 24.99, TRUE, 'Nike', 'accessorio', 'unisex', 130),
('P000000017', 'CompressionSock Run', 'Calza a compressione graduata per running e recupero muscolare', 19.99, TRUE, 'CEP', 'accessorio', 'unisex', 200),
('P000000018', 'SportBand Elite', 'Fascia da testa in microfibra antiscivolo, assorbe il sudore', 14.99, TRUE, 'Under Armour', 'accessorio', 'unisex', 250),
('P000000019', 'TrailPack 10L', 'Zaino da trail running ultraleggero da 10 litri con tasca per idratazione', 89.99, TRUE, 'Osprey', 'accessorio', 'unisex', 40),
('P000000020', 'SmartWatch Sport', 'Smartwatch sportivo con GPS, cardiofrequenzimetro e impermeabile 50m', 199.99, TRUE, 'Garmin', 'accessorio', 'unisex', 35);

-- ==============================================
-- SCARPE (dettagli)
-- ==============================================
INSERT INTO Scarpe (id_prodotto, tipo_suola, materiale) VALUES
('P000000001', 'Foam + Carbonio', 'Mesh sintetica traspirante'),
('P000000002', 'Vibram outdoor', 'Goretex impermeabile'),
('P000000003', 'Racing flat', 'Mesh ultraleggera'),
('P000000004', 'Gomma piatta multidirezionale', 'Pelle sintetica + Mesh'),
('P000000005', 'Gomma terra rossa', 'Mesh rinforzata'),
('P000000006', 'EVA soft', 'Mesh traspirante morbida');

-- ==============================================
-- VESTITI (dettagli)
-- ==============================================
INSERT INTO Vestiti (id_prodotto, tipo_vita, tessuto, stagione, categoriaVestito, tipo_collo, manica, gamba) VALUES
('P000000007', 'Standard', 'Poliestere 100% DryFit', 'Estate', 'maglietta', 'Rotondo', 'corta', NULL),
('P000000008', 'Alta', 'Nylon elasticizzato 80% / Elastan 20%', 'Tutto anno', 'pantalone', NULL, NULL, 'lunghi'),
('P000000009', 'Elastica', 'Poliestere stretch 4-way', 'Tutto anno', 'tuta', 'Con cappuccio', 'lunga', 'lunghi'),
('P000000010', 'Standard', 'Poliestere mesh 100%', 'Estate', 'maglietta', 'A V', 'corta', NULL),
('P000000011', 'Elastica', 'Poliestere + Pile interno', 'Inverno', 'pantalone', NULL, NULL, 'lunghi'),
('P000000012', 'Elastica', 'Cotone-Poliestere 50/50', 'Tutto anno', 'tuta', 'Rotondo', 'lunga', 'lunghi'),
('P000000013', 'Bassa', 'Nylon rinforzato', 'Tutto anno', 'pantalone', NULL, NULL, 'corti'),
('P000000014', 'Alta', 'Nylon opaco 4-way stretch', 'Tutto anno', 'pantalone', NULL, NULL, 'lunghi');

-- ==============================================
-- ACCESSORI (dettagli)
-- ==============================================
INSERT INTO Accessori (id_prodotto, tipo_accessorio, materiale) VALUES
('P000000015', 'Cintura porta-borraccia', 'Neoprene + Nylon'),
('P000000016', 'Guanti da palestra', 'Pelle sintetica + Neoprene'),
('P000000017', 'Calzini a compressione', 'Nylon + Elastan'),
('P000000018', 'Fascia per capelli', 'Microfibra'),
('P000000019', 'Zaino da running', 'Ripstop Nylon'),
('P000000020', 'Smartwatch', 'Silicone + Vetro temperato');

-- ==============================================
-- SUPPORTO COLORI (prodotto <-> colore)
-- ==============================================
INSERT INTO SupportoColore (id_prodotto, nome) VALUES
-- Scarpe
('P000000001', 'Nero'), ('P000000001', 'Bianco'), ('P000000001', 'Rosso'),
('P000000002', 'Blu'), ('P000000002', 'Grigio'), ('P000000002', 'Nero'),
('P000000003', 'Giallo Fluo'), ('P000000003', 'Nero'), ('P000000003', 'Bianco'),
('P000000004', 'Nero'), ('P000000004', 'Grigio'),
('P000000005', 'Bianco'), ('P000000005', 'Celeste'), ('P000000005', 'Nero'),
('P000000006', 'Rosso'), ('P000000006', 'Blu'), ('P000000006', 'Verde'),
-- Vestiti
('P000000007', 'Nero'), ('P000000007', 'Bianco'), ('P000000007', 'Grigio'), ('P000000007', 'Blu'),
('P000000008', 'Nero'), ('P000000008', 'Navy'), ('P000000008', 'Grigio'),
('P000000009', 'Nero'), ('P000000009', 'Grigio'),
('P000000010', 'Bianco'), ('P000000010', 'Rosso'), ('P000000010', 'Giallo Fluo'),
('P000000011', 'Nero'), ('P000000011', 'Navy'),
('P000000012', 'Rosso'), ('P000000012', 'Blu'), ('P000000012', 'Verde'),
('P000000013', 'Nero'), ('P000000013', 'Grigio'),
('P000000014', 'Nero'), ('P000000014', 'Navy'), ('P000000014', 'Verde'),
-- Accessori
('P000000015', 'Nero'), ('P000000015', 'Arancione'),
('P000000016', 'Nero'), ('P000000016', 'Bianco'),
('P000000017', 'Nero'), ('P000000017', 'Bianco'),
('P000000018', 'Nero'), ('P000000018', 'Grigio'), ('P000000018', 'Rosso'),
('P000000019', 'Nero'), ('P000000019', 'Arancione'),
('P000000020', 'Nero'), ('P000000020', 'Grigio');

-- ==============================================
-- SUPPORTO TAGLIE (prodotto <-> taglia)
-- ==============================================
INSERT INTO SupportoTaglia (id_prodotto, taglia) VALUES
-- Scarpe uomo (41-45)
('P000000001', '41'), ('P000000001', '42'), ('P000000001', '43'), ('P000000001', '44'), ('P000000001', '45'),
-- Scarpe donna (38-42)
('P000000002', '38'), ('P000000002', '39'), ('P000000002', '40'), ('P000000002', '41'), ('P000000002', '42'),
-- Scarpe unisex (38-45)
('P000000003', '38'), ('P000000003', '39'), ('P000000003', '40'), ('P000000003', '41'),
('P000000003', '42'), ('P000000003', '43'), ('P000000003', '44'), ('P000000003', '45'),
-- Scarpe uomo (40-45)
('P000000004', '40'), ('P000000004', '41'), ('P000000004', '42'), ('P000000004', '43'), ('P000000004', '44'), ('P000000004', '45'),
-- Scarpe donna (37-42)
('P000000005', '38'), ('P000000005', '39'), ('P000000005', '40'), ('P000000005', '41'), ('P000000005', '42'),
-- Scarpe bambino (taglia tipicamente più piccola, uso taglie disponibili)
('P000000006', '38'), ('P000000006', '39'), ('P000000006', '40'),
-- Vestiti adulti
('P000000007', 'S'), ('P000000007', 'M'), ('P000000007', 'L'), ('P000000007', 'XL'), ('P000000007', 'XXL'),
('P000000008', 'XS'), ('P000000008', 'S'), ('P000000008', 'M'), ('P000000008', 'L'), ('P000000008', 'XL'),
('P000000009', 'S'), ('P000000009', 'M'), ('P000000009', 'L'), ('P000000009', 'XL'), ('P000000009', 'XXL'),
('P000000010', 'XS'), ('P000000010', 'S'), ('P000000010', 'M'), ('P000000010', 'L'), ('P000000010', 'XL'),
('P000000011', 'S'), ('P000000011', 'M'), ('P000000011', 'L'), ('P000000011', 'XL'), ('P000000011', 'XXL'),
('P000000013', 'S'), ('P000000013', 'M'), ('P000000013', 'L'), ('P000000013', 'XL'), ('P000000013', 'XXL'),
('P000000014', 'XS'), ('P000000014', 'S'), ('P000000014', 'M'), ('P000000014', 'L'), ('P000000014', 'XL'),
-- Vestiti bambino
('P000000012', 'XS'), ('P000000012', 'S'), ('P000000012', 'M'),
-- Accessori taglia unica
('P000000015', 'TU'), ('P000000016', 'TU'), ('P000000017', 'TU'),
('P000000018', 'TU'), ('P000000019', 'TU'), ('P000000020', 'TU');

-- ==============================================
-- IMMAGINI
-- ==============================================
INSERT INTO Immagine (pathname, mime_type, id_prodotto) VALUES
('/img/scarpe/airrun-pro-3-nero.jpg', 'image/jpeg', 'P000000001'),
('/img/scarpe/airrun-pro-3-bianco.jpg', 'image/jpeg', 'P000000001'),
('/img/scarpe/cloudflex-ultra-blu.jpg', 'image/jpeg', 'P000000002'),
('/img/scarpe/speedforce-x-fluo.jpg', 'image/jpeg', 'P000000003'),
('/img/scarpe/gympower-elite-nero.jpg', 'image/jpeg', 'P000000004'),
('/img/scarpe/tennispro-flash-bianco.jpg', 'image/jpeg', 'P000000005'),
('/img/scarpe/kidsrun-star-rosso.jpg', 'image/jpeg', 'P000000006'),
('/img/vestiti/dryfit-tee-nero.jpg', 'image/jpeg', 'P000000007'),
('/img/vestiti/dryfit-tee-bianco.jpg', 'image/jpeg', 'P000000007'),
('/img/vestiti/compression-legging-nero.jpg', 'image/jpeg', 'P000000008'),
('/img/vestiti/flextrack-jogger-nero.jpg', 'image/jpeg', 'P000000009'),
('/img/vestiti/aeromesh-tee-bianco.jpg', 'image/jpeg', 'P000000010'),
('/img/vestiti/thermorun-pant-navy.jpg', 'image/jpeg', 'P000000011'),
('/img/vestiti/kidssport-tuta-blu.jpg', 'image/jpeg', 'P000000012'),
('/img/vestiti/powerlift-shorts-nero.jpg', 'image/jpeg', 'P000000013'),
('/img/vestiti/yogaflow-legging-nero.jpg', 'image/jpeg', 'P000000014'),
('/img/accessori/hydrorun-belt-nero.jpg', 'image/jpeg', 'P000000015'),
('/img/accessori/progrip-gloves-nero.jpg', 'image/jpeg', 'P000000016'),
('/img/accessori/compression-sock.jpg', 'image/jpeg', 'P000000017'),
('/img/accessori/sportband-elite-nero.jpg', 'image/jpeg', 'P000000018'),
('/img/accessori/trailpack-10l-nero.jpg', 'image/jpeg', 'P000000019'),
('/img/accessori/smartwatch-sport-nero.jpg', 'image/jpeg', 'P000000020');

-- ==============================================
-- ORDINI
-- ==============================================
INSERT INTO Ordine (id_ordine, data_ordine, stato_ordine, prezzo_totale, id_utente, id_indirizzo) VALUES
('ORD000000001', '2025-01-10 09:23:00', 'consegnato', 184.98, 'U00002', 2),
('ORD000000002', '2025-01-15 14:45:00', 'consegnato', 129.99, 'U00003', 3),
('ORD000000003', '2025-02-02 11:10:00', 'consegnato', 274.97, 'U00004', 4),
('ORD000000004', '2025-02-18 16:30:00', 'in_spedizione', 94.98, 'U00005', 5),
('ORD000000005', '2025-03-05 08:55:00', 'in_preparazione', 199.99, 'U00006', 6),
('ORD000000006', '2025-03-12 20:10:00', 'annullato', 64.99, 'U00007', 7),
('ORD000000007', '2025-03-20 13:00:00', 'in_spedizione', 349.97, 'U00002', 2),
('ORD000000008', '2025-04-01 10:05:00', 'in_preparazione', 59.98, 'U00008', 8);

-- ==============================================
-- DETTAGLI ORDINE
-- ==============================================
INSERT INTO DettagliOrdine (quantità, prezzo_unitario, id_ordine, id_prodotto) VALUES
-- Ordine 1: maglietta + calzini
(2, 34.99, 'ORD000000001', 'P000000007'),
(3, 19.99, 'ORD000000001', 'P000000017'),

-- Ordine 2: scarpe trail donna
(1, 129.99, 'ORD000000002', 'P000000002'),

-- Ordine 3: scarpe gara + legging + fascia
(1, 179.99, 'ORD000000003', 'P000000003'),
(1, 64.99,  'ORD000000003', 'P000000008'),
(2, 14.99,  'ORD000000003', 'P000000018'),

-- Ordine 4: guanti + calzini x3
(1, 24.99, 'ORD000000004', 'P000000016'),
(2, 34.99, 'ORD000000004', 'P000000007'),

-- Ordine 5: smartwatch
(1, 199.99, 'ORD000000005', 'P000000020'),

-- Ordine 6: legging (annullato)
(1, 64.99, 'ORD000000006', 'P000000008'),

-- Ordine 7: scarpe running + zaino trail + maglietta x2
(1, 149.99, 'ORD000000007', 'P000000001'),
(1, 89.99,  'ORD000000007', 'P000000019'),
(2, 34.99,  'ORD000000007', 'P000000007'),

-- Ordine 8: tuta bambino + scarpe bambino
(1, 49.99, 'ORD000000008', 'P000000012'),
(1, 59.99, 'ORD000000008', 'P000000006');