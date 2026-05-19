package com.example.projet_sigl.dto;

import com.example.projet_sigl.enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministrateurDto {
    private Long idUtilisateur;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    @NotBlank
    @Email
    private String email;

    /** Mot de passe : seulement en entrée (création), jamais retourné. */
    private String motDePasse;

    private RoleType role;
    private String niveauAcces;
    private LocalDateTime dateCreation;
    private LocalDateTime derniereConnexion;
    private Boolean actif;
}
