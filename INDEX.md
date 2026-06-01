# Architecture du Backend - Projet SIGL

## Overview
Système de gestion des stages et soutenances pour écoles (ESEO).

## Structure du projet

```
Projet-SIGL-Back/
├── src/
│   ├── main/
│   │   ├── java/com/example/projet_sigl/
│   │   │   ├── config/        # Configurations Spring
│   │   │   ├── controller/    # REST Controllers
│   │   │   ├── dto/           # Data Transfer Objects
│   │   │   ├── entity/        # JPA Entities
│   │   │   ├── enums/         # Énumérations
│   │   │   ├── exception/     # Exceptions personnalisées
│   │   │   ├── mapper/        # Mappers entre Entities/DTOs
│   │   │   ├── repository/    # JPA Repositories
│   │   │   ├── security/      # Configuration Security
│   │   │   ├── service/       # Business Logic Services
│   │   │   └── ProjetSiglApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-test.properties
│   │       └── db/migrations/  # Flyway migrations
│   └── test/
│       └── java/com/example/projet_sigl/
├── pom.xml                     # Maven configuration
├── mvnw                        # Maven Wrapper (Linux/Mac)
├── mvnw.cmd                    # Maven Wrapper (Windows)
├── INDEX.md                    # Ce fichier
├── structureBDD.md            # Structure de la base de données
└── init-test-users.sql        # Données de test initiales
```

## Entités (Entities)

### Salle **[NEW v2.1]**
- **Classe** : `entity/Salle.java`
- **Table** : `salle`
- **Champs** : id_salle, nom_salle, localisation

### Soutenance
- **Classe** : `entity/Soutenance.java`
- **Modifications v2.1** : Ajout id_salle, id_jury, observation
- **Champs** : id_soutenance, date_soutenance, note_finale, **observation (NEW)**, id_stage, **id_salle (NEW)**, **id_jury (NEW)**

### Autres entités
Stage, Apprenant, Enseignant, Jury, Module, Filière, Entreprise, RapportStage, etc.

## Controllers et Endpoints

| Resource | Base Path | NEW |
|----------|-----------|-----|
| Soutenances | `/api/soutenances` | ✓ PATCH /verdict |
| **Salles** | **/api/salles** | ✓ NEW |
| Juries | `/api/juries` | - |
| Stages | `/api/stages` | - |

## Services

- **SalleService** (NEW) - CRUD complet pour salles
- **SoutenanceService** (amélioré) - Gestion soutenances avec salle/jury
- Autres services existants

## Base de données (Migrations Flyway)

- **V20260601__add_salle_and_extend_soutenance.sql** (NEW)
  - Crée table `salle`
  - Ajoute colonnes à `soutenance`

## Dernière mise à jour
**v2.1.0** - 2026-06-01 - Gestion des Soutenances

