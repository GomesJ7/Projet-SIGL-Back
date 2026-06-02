package com.example.projet_sigl.controller;

import com.example.projet_sigl.dto.PromotionDto;
import com.example.projet_sigl.service.PromotionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService service;
    private static final Logger logger = LoggerFactory.getLogger(PromotionController.class);

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT','APPRENANT')")
    public List<PromotionDto> findAll() {
        logger.info("Fetching all promotions");
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT','APPRENANT')")
    public PromotionDto findById(@PathVariable Long id) {
        logger.info("Fetching promotion with id: {}", id);
        return service.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PromotionDto> create(@Valid @RequestBody PromotionDto dto) {
        logger.info("Creating promotion: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public PromotionDto update(@PathVariable Long id, @Valid @RequestBody PromotionDto dto) {
        logger.info("Updating promotion with id: {}", id);
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting promotion with id: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
