package com.example.projet_sigl.mapper;

import com.example.projet_sigl.dto.ModuleDto;
import com.example.projet_sigl.entity.Module;

public final class ModuleMapper {

    private ModuleMapper() {}

    public static ModuleDto toDto(Module m) {
        if (m == null) return null;
        return new ModuleDto(m.getIdModule(), m.getCodeModule(), m.getLibelle(), m.getCredits());
    }

    public static Module toEntity(ModuleDto dto) {
        Module m = new Module();
        m.setIdModule(dto.getIdModule());
        m.setCodeModule(dto.getCodeModule());
        m.setLibelle(dto.getLibelle());
        m.setCredits(dto.getCredits());
        return m;
    }
}
