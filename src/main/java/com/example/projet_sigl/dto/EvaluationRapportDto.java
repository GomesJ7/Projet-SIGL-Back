package com.example.projet_sigl.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Payload d'évaluation d'un rapport par un enseignant : note + commentaire.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationRapportDto {

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("20.0")
    private BigDecimal note;

    private String commentaire;
}
