package com.example.projet_sigl.mapper;

import com.example.projet_sigl.dto.SalleDto;
import com.example.projet_sigl.entity.Salle;

public class SalleMapper {

    public static SalleDto toDto(Salle entity) {
        if (entity == null) return null;
        return new SalleDto(entity.getIdSalle(), entity.getNomSalle(), entity.getLocalisation());
    }

    public static Salle toEntity(SalleDto dto) {
        if (dto == null) return null;
        Salle entity = new Salle();
        entity.setIdSalle(dto.getIdSalle());
        entity.setNomSalle(dto.getNomSalle());
        entity.setLocalisation(dto.getLocalisation());
        return entity;
    }
}

