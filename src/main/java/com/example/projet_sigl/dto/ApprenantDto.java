package com.example.projet_sigl.dto;

import com.example.projet_sigl.enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprenantDto {
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
    private String matricule;
    private String niveau;
    private LocalDate dateNaissance;
    private Long idPromotion;
    private String nomPromotion; // pratique pour l'affichage côté front
    private Long idFiliere;
    private String nomFiliere; // pratique pour l'affichage côté front
}
