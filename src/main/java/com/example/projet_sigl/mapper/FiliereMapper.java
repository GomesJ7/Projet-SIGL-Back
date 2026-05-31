package com.example.projet_sigl.mapper;

import com.example.projet_sigl.dto.FiliereDto;
import com.example.projet_sigl.entity.Filiere;

public final class FiliereMapper {

    private FiliereMapper() {}

    public static FiliereDto toDto(Filiere f) {
        if (f == null) return null;
        return new FiliereDto(f.getIdFiliere(), f.getNomFiliere());
    }

    public static Filiere toEntity(FiliereDto dto) {
        Filiere f = new Filiere();
        f.setIdFiliere(dto.getIdFiliere());
        f.setNomFiliere(dto.getNomFiliere());
        return f;
    }
}

