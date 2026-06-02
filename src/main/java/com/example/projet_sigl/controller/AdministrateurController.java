package com.example.projet_sigl.controller;

import com.example.projet_sigl.dto.AdministrateurDto;
import com.example.projet_sigl.service.AdministrateurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administrateurs")
@RequiredArgsConstructor
public class AdministrateurController {

    private final AdministrateurService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT','APPRENANT')")
    public List<AdministrateurDto> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT','APPRENANT')")
    public AdministrateurDto findById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdministrateurDto> create(@Valid @RequestBody AdministrateurDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public AdministrateurDto update(@PathVariable Long id, @Valid @RequestBody AdministrateurDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
