-- Script d'initialisation des utilisateurs de test
-- Les mots de passe sont hachés avec BCrypt (password : "password")
-- Hash BCrypt de "password" : $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeakeJQm9K6pPSexY68m.

-- Utilisateur ADMIN
INSERT INTO utilisateur (dtype, email, mot_de_passe, nom, prenom, role, date_creation, actif) 
VALUES ('Administrateur', 'admin@eseo.fr', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeakeJQm9K6pPSexY68m.', 'Admin', 'System', 'ADMIN', NOW(), true)
ON DUPLICATE KEY UPDATE mot_de_passe = VALUES(mot_de_passe);

-- Utilisateur ENSEIGNANT
INSERT INTO utilisateur (dtype, email, mot_de_passe, nom, prenom, role) 
VALUES ('Enseignant', 'teacher@eseo.fr', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeakeJQm9K6pPSexY68m.', 'Dupont', 'Jean', 'ENSEIGNANT')
ON DUPLICATE KEY UPDATE mot_de_passe = VALUES(mot_de_passe);

-- Utilisateur APPRENANT
INSERT INTO utilisateur (dtype, email, mot_de_passe, nom, prenom, role, matricule) 
VALUES ('Apprenant', 'student@eseo.fr', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeakeJQm9K6pPSexY68m.', 'Martin', 'Pierre', 'APPRENANT', 'APPR-2026-00001-001')
ON DUPLICATE KEY UPDATE mot_de_passe = VALUES(mot_de_passe);

-- Vérifier l'insertion
SELECT id_utilisateur, email, nom, prenom, role FROM utilisateur WHERE email IN ('admin@eseo.fr', 'teacher@eseo.fr', 'student@eseo.fr');
