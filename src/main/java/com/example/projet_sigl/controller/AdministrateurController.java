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
@PreAuthorize("hasRole('ADMIN')")
public class AdministrateurController {

    private final AdministrateurService service;

    @GetMapping
    public List<AdministrateurDto> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public AdministrateurDto findById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<AdministrateurDto> create(@Valid @RequestBody AdministrateurDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public AdministrateurDto update(@PathVariable Long id, @Valid @RequestBody AdministrateurDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
