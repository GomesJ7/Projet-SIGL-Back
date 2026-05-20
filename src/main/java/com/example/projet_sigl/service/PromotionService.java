package com.example.projet_sigl.service;

import com.example.projet_sigl.dto.PromotionDto;
import com.example.projet_sigl.entity.Promotion;
import com.example.projet_sigl.exception.ResourceNotFoundException;
import com.example.projet_sigl.mapper.PromotionMapper;
import com.example.projet_sigl.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PromotionService {

    private final PromotionRepository repo;
    private static final Logger logger = LoggerFactory.getLogger(PromotionService.class);

    public List<PromotionDto> findAll() {
        logger.info("Fetching all promotions");
        return repo.findAll().stream().map(PromotionMapper::toDto).toList();
    }

    public PromotionDto findById(Long id) {
        logger.info("Fetching promotion with id: {}", id);
        return PromotionMapper.toDto(
                repo.findById(id).orElseThrow(() -> {
                    logger.error("Promotion not found with id: {}", id);
                    return ResourceNotFoundException.of("Promotion", id);
                })
        );
    }

    public PromotionDto create(PromotionDto dto) {
        Promotion p = PromotionMapper.toEntity(dto);
        p.setIdPromotion(null);
        logger.info("Saving promotion: {}", dto);
        return PromotionMapper.toDto(repo.save(p));
    }

    public PromotionDto update(Long id, PromotionDto dto) {
        Promotion p = repo.findById(id).orElseThrow(() -> ResourceNotFoundException.of("Promotion", id));
        p.setNomPromotion(dto.getNomPromotion());
        p.setAnnee(dto.getAnnee());
        logger.info("Updating promotion with id: {}", id);
        return PromotionMapper.toDto(repo.save(p));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw ResourceNotFoundException.of("Promotion", id);
        logger.info("Deleting promotion with id: {}", id);
        repo.deleteById(id);
    }
}
