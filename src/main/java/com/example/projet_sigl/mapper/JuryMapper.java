package com.example.projet_sigl.mapper;

import com.example.projet_sigl.dto.JuryDto;
import com.example.projet_sigl.entity.Jury;
import org.springframework.stereotype.Component;

@Component
public class JuryMapper {
    public JuryDto toDto(Jury jury) {
        if (jury == null) return null;
        return new JuryDto(jury.getIdJury(), jury.getNomJury());
    }

    public Jury toEntity(JuryDto dto) {
        if (dto == null) return null;
        return new Jury(dto.getIdJury(), dto.getNomJury());
    }
}
