package com.example.projet_sigl.controller;

import com.example.projet_sigl.dto.EvaluationRapportDto;
import com.example.projet_sigl.dto.RapportStageDto;
import com.example.projet_sigl.enums.StatutType;
import com.example.projet_sigl.service.RapportStageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/rapports")
@RequiredArgsConstructor
public class RapportStageController {

    private final RapportStageService service;
    private static final Logger logger = LoggerFactory.getLogger(RapportStageController.class);

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public List<RapportStageDto> findAll(@RequestParam(required = false) StatutType statut) {
        logger.info("Fetching all reports with status: {}", statut);
        return statut == null ? service.findAll() : service.findByStatut(statut);
    }

    @GetMapping("/{id}")
    public RapportStageDto findById(@PathVariable Long id) {
        logger.info("Fetching report with id: {}", id);
        return service.findById(id);
    }

    @GetMapping("/apprenant/{idApprenant}")
    public List<RapportStageDto> findByApprenant(@PathVariable Long idApprenant) {
        return service.findByApprenant(idApprenant);
    }

    /**
     * Dépôt d'un rapport. Multipart : `fichier` (PDF) + paramètres `idStage` et `idApprenant`.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('APPRENANT','ADMIN')")
    public ResponseEntity<RapportStageDto> deposer(@RequestParam Long idStage,
                                                   @RequestParam Long idApprenant,
                                                   @RequestPart("fichier") MultipartFile fichier) {
        logger.info("Depositing report for stage {} and apprenant {}", idStage, idApprenant);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.deposer(idStage, idApprenant, fichier));
    }

    @GetMapping("/{id}/fichier")
    public ResponseEntity<Resource> telecharger(@PathVariable Long id) {
        Resource res = service.telecharger(id);
        logger.info("Downloading file: {}", res.getFilename());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + res.getFilename() + "\"")
                .body(res);
    }

    @PutMapping("/{id}/evaluation")
    @PreAuthorize("hasRole('ENSEIGNANT')")
    public RapportStageDto evaluer(@PathVariable Long id, @Valid @RequestBody EvaluationRapportDto dto) {
        return service.evaluer(id, dto);
    }

    @PatchMapping("/{id}/valider")
    @PreAuthorize("hasRole('ENSEIGNANT')")
    public RapportStageDto valider(@PathVariable Long id) { return service.valider(id); }

    @PatchMapping("/{id}/rejeter")
    @PreAuthorize("hasRole('ENSEIGNANT')")
    public RapportStageDto rejeter(@PathVariable Long id) { return service.rejeter(id); }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
