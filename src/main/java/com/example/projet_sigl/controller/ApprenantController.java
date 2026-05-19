package com.example.projet_sigl.controller;

import com.example.projet_sigl.dto.ApprenantDto;
import com.example.projet_sigl.dto.StageDto;
import com.example.projet_sigl.service.ApprenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apprenants")
@RequiredArgsConstructor
public class ApprenantController {

    private final ApprenantService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public List<ApprenantDto> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ApprenantDto findById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApprenantDto> create(@Valid @RequestBody ApprenantDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApprenantDto update(@PathVariable Long id, @Valid @RequestBody ApprenantDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/promotion/{idPromotion}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApprenantDto affecterPromotion(@PathVariable Long id, @PathVariable Long idPromotion) {
        return service.affecterPromotion(id, idPromotion);
    }

    @GetMapping("/{id}/stages")
    public List<StageDto> getHistoriqueStages(@PathVariable Long id) {
        return service.getHistoriqueStages(id);
    }

    @GetMapping("/promotion/{idPromotion}")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public List<ApprenantDto> findByPromotion(@PathVariable Long idPromotion) {
        return service.findByPromotion(idPromotion);
    }
}
