package com.example.projet_sigl.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoutenanceVerdictDto {
    
    @DecimalMin(value = "0.0", message = "La note doit être entre 0 et 20")
    @DecimalMax(value = "20.0", message = "La note doit être entre 0 et 20")
    private BigDecimal noteFinale;
    
    private String observation;
}
