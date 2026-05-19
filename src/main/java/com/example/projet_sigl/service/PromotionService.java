package com.example.projet_sigl.service;

import com.example.projet_sigl.dto.PromotionDto;
import com.example.projet_sigl.entity.Promotion;
import com.example.projet_sigl.exception.ResourceNotFoundException;
import com.example.projet_sigl.mapper.PromotionMapper;
import com.example.projet_sigl.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PromotionService {

    private final PromotionRepository repo;

    public List<PromotionDto> findAll() {
        return repo.findAll().stream().map(PromotionMapper::toDto).toList();
    }

    public PromotionDto findById(Long id) {
        return PromotionMapper.toDto(
                repo.findById(id).orElseThrow(() -> ResourceNotFoundException.of("Promotion", id))
        );
    }

    public PromotionDto create(PromotionDto dto) {
        Promotion p = PromotionMapper.toEntity(dto);
        p.setIdPromotion(null);
        return PromotionMapper.toDto(repo.save(p));
    }

    public PromotionDto update(Long id, PromotionDto dto) {
        Promotion p = repo.findById(id).orElseThrow(() -> ResourceNotFoundException.of("Promotion", id));
        p.setNomPromotion(dto.getNomPromotion());
        p.setAnnee(dto.getAnnee());
        return PromotionMapper.toDto(repo.save(p));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw ResourceNotFoundException.of("Promotion", id);
        repo.deleteById(id);
    }
}
