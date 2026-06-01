package com.example.projet_sigl.mapper;

import com.example.projet_sigl.dto.JuryDto;
import com.example.projet_sigl.entity.Jury;

public final class JuryMapper {

    private JuryMapper() {}

    public static JuryDto toDto(Jury jury) {
        if (jury == null) return null;
        JuryDto dto = new JuryDto();
        dto.setIdJury(jury.getIdJury());
        dto.setNomJury(jury.getNomJury());
        return dto;
    }

    public static Jury toEntity(JuryDto dto) {
        Jury jury = new Jury();
        jury.setIdJury(dto.getIdJury());
        jury.setNomJury(dto.getNomJury());
        return jury;
    }
}

