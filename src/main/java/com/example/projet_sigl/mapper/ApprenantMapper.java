package com.example.projet_sigl.mapper;

import com.example.projet_sigl.dto.ApprenantDto;
import com.example.projet_sigl.entity.Apprenant;

public final class ApprenantMapper {

    private ApprenantMapper() {}

    public static ApprenantDto toDto(Apprenant a) {
        if (a == null) return null;
        ApprenantDto dto = new ApprenantDto();
        dto.setIdUtilisateur(a.getIdUtilisateur());
        dto.setNom(a.getNom());
        dto.setPrenom(a.getPrenom());
        dto.setEmail(a.getEmail());
        dto.setRole(a.getRole());
        dto.setMatricule(a.getMatricule());
        dto.setNiveau(a.getNiveau());
        dto.setDateNaissance(a.getDateNaissance());
        if (a.getPromotion() != null) {
            dto.setIdPromotion(a.getPromotion().getIdPromotion());
            dto.setNomPromotion(a.getPromotion().getNomPromotion());
        }
        if (a.getFiliere() != null) {
            dto.setIdFiliere(a.getFiliere().getIdFiliere());
            dto.setNomFiliere(a.getFiliere().getNomFiliere());
        }
        return dto;
    }

    public static Apprenant toEntity(ApprenantDto dto) {
        Apprenant a = new Apprenant();
        a.setIdUtilisateur(dto.getIdUtilisateur());
        a.setNom(dto.getNom());
        a.setPrenom(dto.getPrenom());
        a.setEmail(dto.getEmail());
        a.setMotDePasse(dto.getMotDePasse());
        a.setRole(dto.getRole());
        a.setMatricule(dto.getMatricule());
        a.setNiveau(dto.getNiveau());
        a.setDateNaissance(dto.getDateNaissance());
        // promotion gérée par le service (lookup en base)
        return a;
    }
}
