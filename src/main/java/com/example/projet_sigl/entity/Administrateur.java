package com.example.projet_sigl.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Administrateur : sous-classe de Utilisateur.
 * Mappée sur la table `administrateur` qui partage la PK `id_utilisateur` avec `utilisateur`.
 */
@Entity
@Table(name = "administrateur")
@PrimaryKeyJoinColumn(name = "id_utilisateur")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Administrateur extends Utilisateur {

    @Column(name = "niveau_acces", length = 50)
    private String niveauAcces;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "derniere_connexion")
    private LocalDateTime derniereConnexion;

    @Column(name = "actif")
    private Boolean actif;
}
