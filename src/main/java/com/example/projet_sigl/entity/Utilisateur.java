package com.example.projet_sigl.entity;

import com.example.projet_sigl.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Classe parent de tous les utilisateurs.
 * Stratégie d'héritage JOINED : une table parent `utilisateur` + une table fille
 * par sous-type (administrateur, enseignant, apprenant) partageant la même PK.
 * Reflète exactement les FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur)
 * du structureBDD.md.
 */
@Entity
@Table(name = "utilisateur")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    private Long idUtilisateur;

    @Column(name = "nom", length = 100, nullable = false)
    private String nom;

    @Column(name = "prenom", length = 100, nullable = false)
    private String prenom;

    @Column(name = "email", length = 150, unique = true, nullable = false)
    private String email;

    @Column(name = "mot_de_passe", length = 255, nullable = false)
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20, nullable = false)
    private RoleType role;

    @Column(name = "actif", nullable = false)
    private Boolean actif = true;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    @Column(name = "derniere_connexion")
    private LocalDateTime derniereConnexion;
}
