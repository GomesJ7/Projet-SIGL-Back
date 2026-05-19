package com.example.projet_sigl.dto;

import com.example.projet_sigl.enums.EtatType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StageDto {
    private Long idStage;

    @NotBlank
    private String poste;

    private String objectif;

    @NotNull
    private LocalDate dateDebut;

    private LocalDate dateFin;

    private EtatType etat;
    private Long idEntreprise;
    private String nomEntreprise; // simplifie l'affichage
}
