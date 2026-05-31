package com.example.projet_sigl.service;

import com.example.projet_sigl.dto.auth.AuthResponse;
import com.example.projet_sigl.dto.auth.LoginRequest;
import com.example.projet_sigl.dto.auth.RegisterRequest;
import com.example.projet_sigl.entity.Administrateur;
import com.example.projet_sigl.entity.Apprenant;
import com.example.projet_sigl.entity.Enseignant;
import com.example.projet_sigl.entity.Utilisateur;
import com.example.projet_sigl.enums.RoleType;
import com.example.projet_sigl.exception.DuplicateResourceException;
import com.example.projet_sigl.exception.ResourceNotFoundException;
import com.example.projet_sigl.repository.UtilisateurRepository;
import com.example.projet_sigl.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UtilisateurRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new DuplicateResourceException("Email déjà utilisé : " + req.getEmail());
        }
        // Instancie la bonne sous-classe en fonction du rôle, sinon JPA ne saura pas dans
        // quelle table fille écrire (héritage JOINED).
        Utilisateur u = switch (req.getRole()) {
            case ADMIN -> {
                Administrateur a = new Administrateur();
                a.setDateCreation(LocalDateTime.now());
                a.setActif(true);
                yield a;
            }
            case ENSEIGNANT -> new Enseignant();
            case APPRENANT -> new Apprenant();
        };
        u.setNom(req.getNom());
        u.setPrenom(req.getPrenom());
        u.setEmail(req.getEmail());
        u.setMotDePasse(passwordEncoder.encode(req.getMotDePasse()));
        u.setRole(req.getRole());
        u = userRepo.save(u);
        return buildResponse(u);
    }

    public AuthResponse login(LoginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getMotDePasse())
        );
        Utilisateur u = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> ResourceNotFoundException.of("Utilisateur", req.getEmail()));

        // Persist la date de dernière connexion après authentification réussie.
        u.setDerniereConnexion(LocalDateTime.now());
        u = userRepo.save(u);

        return buildResponse(u);
    }

    private AuthResponse buildResponse(Utilisateur u) {
        String token = jwtService.generateToken(u);
        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .idUtilisateur(u.getIdUtilisateur())
                .email(u.getEmail())
                .nom(u.getNom())
                .prenom(u.getPrenom())
                .role(u.getRole())
                .build();
    }
}
