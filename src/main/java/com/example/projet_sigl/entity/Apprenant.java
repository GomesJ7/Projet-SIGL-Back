package com.example.projet_sigl.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Apprenant : sous-classe de Utilisateur.
 * Lié à une Promotion (filière/année) et historique de stages via affectation_stage.
 */
@Entity
@Table(name = "apprenant")
@PrimaryKeyJoinColumn(name = "id_utilisateur")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Apprenant extends Utilisateur {

    @Column(name = "matricule", length = 50, unique = true)
    private String matricule;

    @Column(name = "niveau", length = 50)
    private String niveau;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_promotion")
    private Promotion promotion;
}
