package com.example.projet_sigl.controller;

import com.example.projet_sigl.dto.JuryDto;
import com.example.projet_sigl.dto.JuryEnseignantDto;
import com.example.projet_sigl.service.JuryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/juries")
@RequiredArgsConstructor
public class JuryController {

    private final JuryService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public List<JuryDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public JuryDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JuryDto> create(@Valid @RequestBody JuryDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public JuryDto update(@PathVariable Long id, @Valid @RequestBody JuryDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idJury}/enseignants/{idEnseignant}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> affecterEnseignant(@PathVariable Long idJury,
                                                   @PathVariable Long idEnseignant,
                                                   @RequestParam(required = false) String roleJury) {
        service.affecterEnseignant(idJury, idEnseignant, roleJury);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{idJury}/enseignants/{idEnseignant}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> desaffecterEnseignant(@PathVariable Long idJury,
                                                      @PathVariable Long idEnseignant) {
        service.desaffecterEnseignant(idJury, idEnseignant);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idJury}/enseignants")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public List<JuryEnseignantDto> getEnseignants(@PathVariable Long idJury) {
        return service.getEnseignants(idJury);
    }

    @GetMapping("/enseignants/{idEnseignant}")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public List<JuryEnseignantDto> getJuriesByEnseignant(@PathVariable Long idEnseignant) {
        return service.getJuriesByEnseignant(idEnseignant);
    }
}

