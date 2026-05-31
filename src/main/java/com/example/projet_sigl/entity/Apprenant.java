package com.example.projet_sigl.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

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

    @Column(name = "niveau", length = 50)
    private String niveau;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    // Suppression du "private Long idPromotion;" qui faisait doublon
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_promotion") // Gère automatiquement la FK id_promotion en BD
    private Promotion promotion;

    // Suppression du "private Long idFiliere;" qui faisait doublon
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_filiere") // Gère automatiquement la FK id_filiere en BD
    private Filiere filiere;
}