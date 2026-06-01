package com.example.projet_sigl.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JuryDto {
    private Long idJury;

    @NotBlank
    private String nomJury;
}

