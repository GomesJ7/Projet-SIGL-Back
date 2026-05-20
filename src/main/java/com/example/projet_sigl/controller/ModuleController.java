package com.example.projet_sigl.controller;

import com.example.projet_sigl.dto.ModuleDto;
import com.example.projet_sigl.service.ModuleService;
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
@RequestMapping("/api/modules")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService service;
    private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);

    @GetMapping
    public List<ModuleDto> findAll() {
        logger.info("Fetching all modules");
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ModuleDto findById(@PathVariable Long id) {
        logger.info("Fetching module with id: {}", id);
        return service.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ModuleDto> create(@Valid @RequestBody ModuleDto dto) {
        logger.info("Creating module: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModuleDto update(@PathVariable Long id, @Valid @RequestBody ModuleDto dto) {
        logger.info("Updating module with id: {}", id);
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting module with id: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idModule}/enseignants/{idEnseignant}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> affecterEnseignant(@PathVariable Long idModule,
                                                   @PathVariable Long idEnseignant) {
        logger.info("Affecting enseignant {} to module {}", idEnseignant, idModule);
        service.affecterEnseignant(idModule, idEnseignant);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{idModule}/enseignants/{idEnseignant}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> desaffecterEnseignant(@PathVariable Long idModule,
                                                      @PathVariable Long idEnseignant) {
        logger.info("Desaffecting enseignant {} from module {}", idEnseignant, idModule);
        service.desaffecterEnseignant(idModule, idEnseignant);
        return ResponseEntity.noContent().build();
    }
}
