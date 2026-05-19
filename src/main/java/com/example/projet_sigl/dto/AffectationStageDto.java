package com.example.projet_sigl.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AffectationStageDto {

    @NotNull
    private Long idStage;

    @NotNull
    private Long idApprenant;

    @NotNull
    private Long idEnseignant;
}
