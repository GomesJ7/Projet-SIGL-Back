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
    private String fichier;
    private BigDecimal note;
    private String commentaire;
    private StatutType statut;
    private Long idStage;
    private Long idApprenant;
}
