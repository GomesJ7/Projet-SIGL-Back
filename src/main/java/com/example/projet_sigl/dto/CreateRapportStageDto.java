package com.example.projet_sigl.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRapportStageDto {
    
    @NotNull(message = "L'ID du stage est obligatoire")
    private Long idStage;
    
    @NotNull(message = "L'ID de l'apprenant est obligatoire")
    private Long idApprenant;
    
    @NotBlank(message = "Le titre du rapport est obligatoire")
    private String titre;
    
    @NotBlank(message = "Le chemin du fichier est obligatoire")
    private String fichierPath;
    
    @NotBlank(message = "Le nom du fichier est obligatoire")
    private String fichier;
    
    @NotBlank(message = "La version du rapport est obligatoire")
    private String versionRapport;
}
