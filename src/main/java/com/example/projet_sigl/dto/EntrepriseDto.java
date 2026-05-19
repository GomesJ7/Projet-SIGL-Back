package com.example.projet_sigl.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntrepriseDto {
    private Long idEntreprise;

    @NotBlank
    private String nomEntreprise;

    @Email
    private String emailEntreprise;

    private String adresseEntreprise;
}
