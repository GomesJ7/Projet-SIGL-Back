package com.example.projet_sigl.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Enseignant : sous-classe de Utilisateur.
 * Lié aux modules via la table enseignant_module (porteuse de date_affectation),
 * et aux stages encadrés via affectation_stage.
 */
@Entity
@Table(name = "enseignant")
@PrimaryKeyJoinColumn(name = "id_utilisateur")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enseignant extends Utilisateur {

    @Column(name = "specialite", length = 100)
    private String specialite;

    @Column(name = "grade", length = 100)
    private String grade;

    /**
     * Affectations aux modules. Le mappedBy pointe vers le champ `enseignant`
     * de l'entité de liaison EnseignantModule.
     */
    @OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EnseignantModule> affectationsModules = new HashSet<>();
}
