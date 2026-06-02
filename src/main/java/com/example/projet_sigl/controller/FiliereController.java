package com.example.projet_sigl.controller;

import com.example.projet_sigl.dto.FiliereDto;
import com.example.projet_sigl.service.FiliereService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filieres")
@RequiredArgsConstructor
public class FiliereController {

    private final FiliereService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT','APPRENANT')")
    public List<FiliereDto> findAll() { return service.findAll(); }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FiliereDto> create(@Valid @RequestBody FiliereDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public FiliereDto update(@PathVariable Long id, @Valid @RequestBody FiliereDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
