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

    @Column(name = "date_depot", nullable = false)
    private LocalDateTime dateDepot;

    @Column(name = "titre", length = 200)
    private String titre;

    /** Référence / lien métier du fichier. */
    @Column(name = "fichier_path", length = 255, nullable = false)
    private String fichierPath;

    /** Contenu binaire du PDF stocké en base (LONGBLOB). */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "fichier", nullable = false)
    private byte[] fichier;

    @Column(name = "nom_fichier", length = 255)
    private String nomFichier;

    @Column(name = "version_rapport", length = 50)
    private String versionRapport;

    @Column(name = "note", precision = 4, scale = 2)
    private BigDecimal note;

    @Column(name = "commentaire", columnDefinition = "TEXT")
    private String commentaire;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", length = 20, nullable = false)
    private StatutType statut;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stage", nullable = false)
    private Stage stage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_apprenant", nullable = false)
    private Apprenant apprenant;
}
