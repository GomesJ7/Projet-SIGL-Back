package com.example.projet_sigl.mapper;

import com.example.projet_sigl.dto.EnseignantDto;
import com.example.projet_sigl.entity.Enseignant;

public final class EnseignantMapper {

    private EnseignantMapper() {}

    public static EnseignantDto toDto(Enseignant e) {
        if (e == null) return null;
        EnseignantDto dto = new EnseignantDto();
        dto.setIdUtilisateur(e.getIdUtilisateur());
        dto.setNom(e.getNom());
        dto.setPrenom(e.getPrenom());
        dto.setEmail(e.getEmail());
        dto.setRole(e.getRole());
        dto.setSpecialite(e.getSpecialite());
        return dto;
    }

    public static Enseignant toEntity(EnseignantDto dto) {
        Enseignant e = new Enseignant();
        e.setIdUtilisateur(dto.getIdUtilisateur());
        e.setNom(dto.getNom());
        e.setPrenom(dto.getPrenom());
        e.setEmail(dto.getEmail());
        e.setMotDePasse(dto.getMotDePasse());
        e.setRole(dto.getRole());
        e.setSpecialite(dto.getSpecialite());
        return e;
    }
}
