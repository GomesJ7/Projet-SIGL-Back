package com.example.projet_sigl.dto.auth;

import com.example.projet_sigl.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String type;       // "Bearer"
    private Long idUtilisateur;
    private String email;
    private String nom;
    private String prenom;
    private RoleType role;
}
