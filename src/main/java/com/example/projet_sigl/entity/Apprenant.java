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

    @Column(name = "matricule", length = 50, unique = true, nullable = false)
    private String matricule;

    @Column(name = "niveau", length = 50, nullable = true)
    private String niveau;

    @Column(name = "date_naissance", nullable = true)
    private LocalDate dateNaissance;

    @Column(name = "id_promotion", insertable = false, updatable = false)
    private Long idPromotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_promotion", nullable = true)
    private Promotion promotion;

    @Column(name = "id_filiere", insertable = false, updatable = false)
    private Long idFiliere;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_filiere", nullable = true)
    private Filiere filiere;
}
