package com.example.projet_sigl.mapper;

import com.example.projet_sigl.dto.RapportStageDto;
import com.example.projet_sigl.entity.RapportStage;

public final class RapportStageMapper {

    private RapportStageMapper() {}

    public static RapportStageDto toDto(RapportStage r) {
        if (r == null) return null;
        RapportStageDto dto = new RapportStageDto();
        dto.setIdRapport(r.getIdRapport());
        dto.setDateDepot(r.getDateDepot());
        dto.setTitre(r.getTitre());
        dto.setFichier(r.getFichier());
        dto.setFichierPath(r.getFichierPath());
        dto.setVersionRapport(r.getVersionRapport());
        dto.setNote(r.getNote());
        dto.setCommentaire(r.getCommentaire());
        dto.setStatut(r.getStatut());
        if (r.getStage() != null) {
            dto.setIdStage(r.getStage().getIdStage());
            dto.setPosteStage(r.getStage().getPoste());
            if (r.getStage().getEntreprise() != null) {
                dto.setNomEntreprise(r.getStage().getEntreprise().getNomEntreprise());
            }
        }
        if (r.getApprenant() != null) {
            dto.setIdApprenant(r.getApprenant().getIdUtilisateur());
            dto.setPrenomApprenant(r.getApprenant().getPrenom());
            dto.setNomApprenant(r.getApprenant().getNom());
        }
        return dto;
    }
}
