package com.example.projet_sigl.controller;

import com.example.projet_sigl.dto.SoutenanceDto;
import com.example.projet_sigl.dto.SoutenanceVerdictDto;
import com.example.projet_sigl.service.SoutenanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/soutenances")
@RequiredArgsConstructor
public class SoutenanceController {

    private final SoutenanceService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT','APPRENANT')")
    public List<SoutenanceDto> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT','APPRENANT')")
    public SoutenanceDto findById(@PathVariable Long id) { return service.findById(id); }

    @GetMapping("/enseignant/{idEnseignant}")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public List<SoutenanceDto> findByEnseignantJury(@PathVariable Long idEnseignant) {
        return service.findByEnseignantJury(idEnseignant);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public ResponseEntity<SoutenanceDto> planifier(@Valid @RequestBody SoutenanceDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.planifier(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public SoutenanceDto update(@PathVariable Long id, @Valid @RequestBody SoutenanceDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/verdict")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public SoutenanceDto donnerVerdict(@PathVariable Long id, @RequestBody SoutenanceVerdictDto dto) {
        return service.updateVerdict(id, dto);
    }
}
