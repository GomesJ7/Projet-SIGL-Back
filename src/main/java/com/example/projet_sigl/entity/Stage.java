package com.example.projet_sigl.entity;

import com.example.projet_sigl.enums.EtatType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "stage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stage")
    private Long idStage;

    @Column(name = "poste", length = 150)
    private String poste;

    @Column(name = "objectif", columnDefinition = "TEXT")
    private String objectif;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat", length = 20)
    private EtatType etat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entreprise")
    private Entreprise entreprise;

    /** Affectations apprenant/enseignant (relation ternaire). */
    @OneToMany(mappedBy = "stage", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AffectationStage> affectations = new HashSet<>();

    /** Rapport associé (1-1, optionnel). */
    @OneToOne(mappedBy = "stage", cascade = CascadeType.ALL, orphanRemoval = true)
    private RapportStage rapport;

    /** Soutenance associée (1-1, optionnel). */
    @OneToOne(mappedBy = "stage", cascade = CascadeType.ALL, orphanRemoval = true)
    private Soutenance soutenance;
}
