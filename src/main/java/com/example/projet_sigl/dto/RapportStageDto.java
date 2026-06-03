package com.example.projet_sigl.dto;

import com.example.projet_sigl.enums.StatutType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RapportStageDto {
    private Long idRapport;
    private LocalDateTime dateDepot;
    private String titre;
    private byte[] fichier;
    private String fichierPath;
    private String nomFichier;
    private String versionRapport;
    private BigDecimal note;
    private String commentaire;
    private StatutType statut;
    private Long idStage;
    private Long idApprenant;
    private String prenomApprenant;
    private String nomApprenant;
    private String posteStage;
    private String nomEntreprise;
}
