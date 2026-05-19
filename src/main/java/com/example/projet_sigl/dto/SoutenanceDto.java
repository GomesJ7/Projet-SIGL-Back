package com.example.projet_sigl.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoutenanceDto {
    private Long idSoutenance;

    @NotNull
    private LocalDateTime dateSoutenance;

    private BigDecimal noteFinale;

    @NotNull
    private Long idStage;
}
