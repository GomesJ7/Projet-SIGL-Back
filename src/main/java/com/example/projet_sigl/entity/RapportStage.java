package com.example.projet_sigl.entity;

import com.example.projet_sigl.enums.StatutType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rapport_stage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RapportStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rapport")
    private Long idRapport;

    @Column(name = "date_depot")
    private LocalDateTime dateDepot;

    /** Chemin du fichier PDF stocké sur le filesystem. */
    @Column(name = "fichier", length = 255)
    private String fichier;

    @Column(name = "note", precision = 5, scale = 2)
    private BigDecimal note;

    @Column(name = "commentaire", columnDefinition = "TEXT")
    private String commentaire;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", length = 20)
    private StatutType statut;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stage", unique = true)
    private Stage stage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_apprenant")
    private Apprenant apprenant;
}
