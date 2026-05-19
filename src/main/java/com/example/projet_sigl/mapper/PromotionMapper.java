package com.example.projet_sigl.mapper;

import com.example.projet_sigl.dto.PromotionDto;
import com.example.projet_sigl.entity.Promotion;

public final class PromotionMapper {

    private PromotionMapper() {}

    public static PromotionDto toDto(Promotion p) {
        if (p == null) return null;
        return new PromotionDto(p.getIdPromotion(), p.getNomPromotion(), p.getAnnee());
    }

    public static Promotion toEntity(PromotionDto dto) {
        Promotion p = new Promotion();
        p.setIdPromotion(dto.getIdPromotion());
        p.setNomPromotion(dto.getNomPromotion());
        p.setAnnee(dto.getAnnee());
        return p;
    }
}
