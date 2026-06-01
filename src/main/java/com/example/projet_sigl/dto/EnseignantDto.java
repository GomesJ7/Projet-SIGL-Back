package com.example.projet_sigl.dto;

import com.example.projet_sigl.enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnseignantDto {
    private Long idUtilisateur;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    @NotBlank
    @Email
    private String email;

    private String motDePasse;

    private RoleType role;
    private String specialite;
    private String grade;
}
