package com.example.projet_sigl.controller;

import com.example.projet_sigl.dto.EnseignantDto;
import com.example.projet_sigl.dto.ModuleDto;
import com.example.projet_sigl.dto.StageDto;
import com.example.projet_sigl.service.EnseignantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enseignants")
@RequiredArgsConstructor
public class EnseignantController {

    private final EnseignantService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public List<EnseignantDto> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public EnseignantDto findById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EnseignantDto> create(@Valid @RequestBody EnseignantDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EnseignantDto update(@PathVariable Long id, @Valid @RequestBody EnseignantDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/modules")
    public List<ModuleDto> getModules(@PathVariable Long id) { return service.getModules(id); }

    @GetMapping("/{id}/stages")
    public List<StageDto> getStagesEncadres(@PathVariable Long id) { return service.getStagesEncadres(id); }
}
