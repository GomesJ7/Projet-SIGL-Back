package com.example.projet_sigl.controller;

import com.example.projet_sigl.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService service;

    @GetMapping("/success-rate")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public Map<String, Object> successRate() {
        return service.getSuccessRate();
    }

    @GetMapping("/by-filiere")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public List<Map<String, Object>> byFiliere() { return service.getRepartitionParFiliere(); }

    @GetMapping("/stages-evolution")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public List<Map<String, Object>> stagesEvolution() { return service.getEvolutionStages(12); }

    @GetMapping("/enseignants-performance")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public List<Map<String, Object>> enseignantsPerformance() { return service.getPerformanceEnseignants(); }
}

