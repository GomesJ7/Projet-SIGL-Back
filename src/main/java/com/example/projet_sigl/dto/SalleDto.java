package com.example.projet_sigl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalleDto {
    private Long idSalle;
    private String nomSalle;
    private String localisation;
}

