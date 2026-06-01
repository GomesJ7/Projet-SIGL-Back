package com.example.projet_sigl.mapper;

import com.example.projet_sigl.dto.SoutenanceDto;
import com.example.projet_sigl.entity.Soutenance;

public final class SoutenanceMapper {

    private SoutenanceMapper() {}

    public static SoutenanceDto toDto(Soutenance s) {
        if (s == null) return null;
        SoutenanceDto dto = new SoutenanceDto();
        dto.setIdSoutenance(s.getIdSoutenance());
        dto.setDateSoutenance(s.getDateSoutenance());
        dto.setNoteFinale(s.getNoteFinale());
        dto.setObservation(s.getObservation());
        if (s.getStage() != null) dto.setIdStage(s.getStage().getIdStage());
        if (s.getSalle() != null) dto.setIdSalle(s.getSalle().getIdSalle());
        if (s.getJury() != null) dto.setIdJury(s.getJury().getIdJury());
        return dto;
    }
}
