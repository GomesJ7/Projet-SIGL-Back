-- Migration : ajout de la colonne id_filiere dans la table apprenant
-- et création de la table filiere si elle n'existe pas encore.
-- A exécuter manuellement dans MySQL si le démarrage du backend ne l'ajoute pas automatiquement.

-- 1. Création de la table filiere (si pas déjà créée par Hibernate)
CREATE TABLE IF NOT EXISTS filiere (
    id_filiere BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom_filiere VARCHAR(100) NOT NULL UNIQUE
);

-- 2. Ajout de la colonne id_filiere dans apprenant
--    (à exécuter uniquement si la colonne n'existe pas déjà)
ALTER TABLE apprenant
    ADD COLUMN id_filiere BIGINT NULL;

-- 3. Ajout de la contrainte de clé étrangère
ALTER TABLE apprenant
    ADD CONSTRAINT fk_apprenant_filiere
        FOREIGN KEY (id_filiere) REFERENCES filiere(id_filiere)
        ON DELETE SET NULL;


