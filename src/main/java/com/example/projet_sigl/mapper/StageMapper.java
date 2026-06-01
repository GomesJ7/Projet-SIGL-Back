package com.example.projet_sigl.mapper;

import com.example.projet_sigl.dto.StageDto;
import com.example.projet_sigl.entity.Stage;

public final class StageMapper {

    private StageMapper() {}

    public static StageDto toDto(Stage s) {
        if (s == null) return null;
        StageDto dto = new StageDto();
        dto.setIdStage(s.getIdStage());
        dto.setPoste(s.getPoste());
        dto.setObjectif(s.getObjectif());
        dto.setDateDebut(s.getDateDebut());
        dto.setDateFin(s.getDateFin());
        dto.setDureeSemaines(s.getDureeSemaines());
        dto.setEtat(s.getEtat());
        if (s.getEntreprise() != null) {
            dto.setIdEntreprise(s.getEntreprise().getIdEntreprise());
            dto.setNomEntreprise(s.getEntreprise().getNomEntreprise());
        }
        return dto;
    }

    public static Stage toEntity(StageDto dto) {
        Stage s = new Stage();
        s.setIdStage(dto.getIdStage());
        s.setPoste(dto.getPoste());
        s.setObjectif(dto.getObjectif());
        s.setDateDebut(dto.getDateDebut());
        s.setDateFin(dto.getDateFin());
        s.setDureeSemaines(dto.getDureeSemaines());
        s.setEtat(dto.getEtat());
        // entreprise gérée par le service
        return s;
    }
}
