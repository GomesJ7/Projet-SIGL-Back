package com.example.projet_sigl.mapper;

import com.example.projet_sigl.dto.AdministrateurDto;
import com.example.projet_sigl.entity.Administrateur;

public final class AdministrateurMapper {

    private AdministrateurMapper() {}

    public static AdministrateurDto toDto(Administrateur a) {
        if (a == null) return null;
        AdministrateurDto dto = new AdministrateurDto();
        dto.setIdUtilisateur(a.getIdUtilisateur());
        dto.setNom(a.getNom());
        dto.setPrenom(a.getPrenom());
        dto.setEmail(a.getEmail());
        dto.setRole(a.getRole());
        dto.setNiveauAcces(a.getNiveauAcces());
        dto.setDateCreation(a.getDateCreation());
        dto.setDerniereConnexion(a.getDerniereConnexion());
        dto.setActif(a.getActif());
        // Le mot de passe n'est volontairement jamais retourné
        return dto;
    }

    public static Administrateur toEntity(AdministrateurDto dto) {
        Administrateur a = new Administrateur();
        a.setIdUtilisateur(dto.getIdUtilisateur());
        a.setNom(dto.getNom());
        a.setPrenom(dto.getPrenom());
        a.setEmail(dto.getEmail());
        a.setMotDePasse(dto.getMotDePasse());
        a.setRole(dto.getRole());
        a.setNiveauAcces(dto.getNiveauAcces());
        a.setDateCreation(dto.getDateCreation());
        a.setDerniereConnexion(dto.getDerniereConnexion());
        a.setActif(dto.getActif());
        return a;
    }
}
