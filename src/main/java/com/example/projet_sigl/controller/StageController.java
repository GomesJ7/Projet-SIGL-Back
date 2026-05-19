package com.example.projet_sigl.controller;

import com.example.projet_sigl.dto.AffectationStageDto;
import com.example.projet_sigl.dto.StageDto;
import com.example.projet_sigl.enums.EtatType;
import com.example.projet_sigl.service.StageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stages")
@RequiredArgsConstructor
public class StageController {

    private final StageService service;

    @GetMapping
    public List<StageDto> findAll(@RequestParam(required = false) EtatType etat) {
        return etat == null ? service.findAll() : service.findByEtat(etat);
    }

    @GetMapping("/{id}")
    public StageDto findById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public ResponseEntity<StageDto> create(@Valid @RequestBody StageDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public StageDto update(@PathVariable Long id, @Valid @RequestBody StageDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /** Changement d'état (EN_COURS -> TERMINE -> VALIDE/REFUSE). */
    @PatchMapping("/{id}/etat")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public StageDto changerEtat(@PathVariable Long id, @RequestParam EtatType etat) {
        return service.changerEtat(id, etat);
    }

    @PostMapping("/affectations")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public ResponseEntity<Void> affecter(@Valid @RequestBody AffectationStageDto dto) {
        service.affecter(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/affectations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> desaffecter(@Valid @RequestBody AffectationStageDto dto) {
        service.desaffecter(dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/affectations")
    public List<AffectationStageDto> getAffectations(@PathVariable Long id) {
        return service.getAffectations(id);
    }
}
