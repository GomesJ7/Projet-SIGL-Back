package com.example.projet_sigl.mapper;

import com.example.projet_sigl.dto.EntrepriseDto;
import com.example.projet_sigl.entity.Entreprise;

public final class EntrepriseMapper {

    private EntrepriseMapper() {}

    public static EntrepriseDto toDto(Entreprise e) {
        if (e == null) return null;
        return new EntrepriseDto(
                e.getIdEntreprise(),
                e.getNomEntreprise(),
                e.getEmailEntreprise(),
                e.getAdresseEntreprise()
        );
    }

    public static Entreprise toEntity(EntrepriseDto dto) {
        Entreprise e = new Entreprise();
        e.setIdEntreprise(dto.getIdEntreprise());
        e.setNomEntreprise(dto.getNomEntreprise());
        e.setEmailEntreprise(dto.getEmailEntreprise());
        e.setAdresseEntreprise(dto.getAdresseEntreprise());
        return e;
    }
}
