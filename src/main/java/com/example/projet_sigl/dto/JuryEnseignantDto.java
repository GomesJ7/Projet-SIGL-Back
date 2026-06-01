package com.example.projet_sigl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JuryEnseignantDto {
    private Long idJury;
    private String nomJury;
    private Long idEnseignant;
    private String nomEnseignant;
    private String prenomEnseignant;
    private String roleJury;
}

