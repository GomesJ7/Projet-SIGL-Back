-- TYPES ENUM
-- ==============================

CREATE TYPE role_type AS ENUM ('ADMIN', 'ENSEIGNANT', 'APPRENANT');
CREATE TYPE etat_type AS ENUM ('EN_COURS', 'TERMINE', 'VALIDE', 'REFUSE');
CREATE TYPE statut_type AS ENUM ('EN_ATTENTE', 'VALIDE', 'REFUSE');

-- ==============================
-- TABLE UTILISATEUR
-- ==============================

CREATE TABLE utilisateur (
    id_utilisateur BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    email VARCHAR(150) UNIQUE,
    mot_de_passe VARCHAR(255),
    role role_type
);

-- ==============================
-- ADMINISTRATEUR
-- ==============================

CREATE TABLE administrateur (
    id_utilisateur BIGINT PRIMARY KEY,
    niveau_acces VARCHAR(50),
    date_creation TIMESTAMP,
    derniere_connexion TIMESTAMP,
    actif BOOLEAN,
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur)
);

-- ==============================
-- ENSEIGNANT
-- ==============================

CREATE TABLE enseignant (
    id_utilisateur BIGINT PRIMARY KEY,
    specialite VARCHAR(100),
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur)
);

-- ==============================
-- PROMOTION
-- ==============================

CREATE TABLE promotion (
    id_promotion BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nom_promotion VARCHAR(100),
    annee INTEGER
);

-- ==============================
-- APPRENANT
-- ==============================

CREATE TABLE apprenant (
    id_utilisateur BIGINT PRIMARY KEY,
    matricule VARCHAR(50),
    niveau VARCHAR(50),
    date_naissance DATE,
    id_promotion BIGINT,
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur),
    FOREIGN KEY (id_promotion) REFERENCES promotion(id_promotion)
);

-- ==============================
-- MODULE
-- ==============================

CREATE TABLE module (
    id_module BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    libelle VARCHAR(100)
);

-- ==============================
-- ENSEIGNANT_MODULE
-- ==============================

CREATE TABLE enseignant_module (
    id_enseignant BIGINT,
    id_module BIGINT,
    date_affectation TIMESTAMP,
    PRIMARY KEY (id_enseignant, id_module),
    FOREIGN KEY (id_enseignant) REFERENCES enseignant(id_utilisateur),
    FOREIGN KEY (id_module) REFERENCES module(id_module)
);

-- ==============================
-- ENTREPRISE
-- ==============================

CREATE TABLE entreprise (
    id_entreprise BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nom_entreprise VARCHAR(150),
    email_entreprise VARCHAR(150),
    adresse_entreprise VARCHAR(255)
);

-- ==============================
-- STAGE
-- ==============================

CREATE TABLE stage (
    id_stage BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    poste VARCHAR(150),
    objectif TEXT,
    date_debut DATE,
    date_fin DATE,
    etat etat_type,
    id_entreprise BIGINT,
    FOREIGN KEY (id_entreprise) REFERENCES entreprise(id_entreprise)
);

-- ==============================
-- AFFECTATION_STAGE (relation ternaire)
-- ==============================

CREATE TABLE affectation_stage (
    id_stage BIGINT,
    id_apprenant BIGINT,
    id_enseignant BIGINT,
    PRIMARY KEY (id_stage, id_apprenant, id_enseignant),
    FOREIGN KEY (id_stage) REFERENCES stage(id_stage),
    FOREIGN KEY (id_apprenant) REFERENCES apprenant(id_utilisateur),
    FOREIGN KEY (id_enseignant) REFERENCES enseignant(id_utilisateur)
);

-- ==============================
-- RAPPORT_STAGE
-- ==============================

CREATE TABLE rapport_stage (
    id_rapport BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    date_depot TIMESTAMP,
    fichier VARCHAR(255),
    note NUMERIC(5,2),
    commentaire TEXT,
    statut statut_type,
    id_stage BIGINT UNIQUE,
    id_apprenant BIGINT,
    FOREIGN KEY (id_stage) REFERENCES stage(id_stage),
    FOREIGN KEY (id_apprenant) REFERENCES apprenant(id_utilisateur)
);

-- ==============================
-- SOUTENANCE
-- ==============================

CREATE TABLE soutenance (
    id_soutenance BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    date_soutenance TIMESTAMP,
    note_finale NUMERIC(5,2),
    id_stage BIGINT UNIQUE,
    FOREIGN KEY (id_stage) REFERENCES stage(id_stage)
);