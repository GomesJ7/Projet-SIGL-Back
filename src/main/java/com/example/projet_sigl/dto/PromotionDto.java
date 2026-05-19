package com.example.projet_sigl.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDto {
    private Long idPromotion;

    @NotBlank
    private String nomPromotion;

    @NotNull
    private Integer annee;
}
