package com.example.projet_sigl.service;

import com.example.projet_sigl.dto.EntrepriseDto;
import com.example.projet_sigl.dto.StageDto;
import com.example.projet_sigl.entity.Enseignant;
import com.example.projet_sigl.entity.Filiere;
import com.example.projet_sigl.enums.StatutType;
import com.example.projet_sigl.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final RapportStageRepository rapportRepo;
    private final FiliereRepository filiereRepo;
    private final ApprenantRepository apprenantRepo;
    private final StageRepository stageRepo;
    private final AffectationStageRepository affRepo;
    private final EnseignantRepository enseignantRepo;

    public Map<String, Object> getSuccessRate() {
        long total = rapportRepo.count();
        long valides = rapportRepo.findByStatut(StatutType.VALIDE).size();
        double rate = total == 0 ? 0.0 : (100.0 * valides) / total;
        Map<String, Object> m = new HashMap<>();
        m.put("totalRapports", total);
        m.put("valides", valides);
        m.put("tauxPourcentage", Math.round(rate * 100.0) / 100.0);
        return m;
    }

    public List<Map<String, Object>> getRepartitionParFiliere() {
        List<Filiere> filieres = filiereRepo.findAll();
        return filieres.stream().map(f -> {
            long cnt = apprenantRepo.countByFiliere_IdFiliere(f.getIdFiliere());
            Map<String, Object> e = new HashMap<>();
            e.put("idFiliere", f.getIdFiliere());
            e.put("nomFiliere", f.getNomFiliere());
            e.put("apprenants", cnt);
            return e;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getEvolutionStages(int monthsBack) {
        YearMonth now = YearMonth.now();
        List<YearMonth> periods = new ArrayList<>();
        for (int i = monthsBack - 1; i >= 0; i--) periods.add(now.minusMonths(i));

        Map<YearMonth, Long> counts = new HashMap<>();
        for (YearMonth p : periods) counts.put(p, 0L);

        stageRepo.findAll().forEach(s -> {
            if (s.getDateDebut() != null) {
                YearMonth ym = YearMonth.from(s.getDateDebut());
                if (counts.containsKey(ym)) counts.put(ym, counts.get(ym) + 1);
            }
        });

        return periods.stream().map(p -> {
            Map<String, Object> m = new HashMap<>();
            m.put("period", p.toString());
            m.put("count", counts.getOrDefault(p, 0L));
            return m;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getPerformanceEnseignants() {
        // count affectations per enseignant
        Map<Long, Long> counts = new HashMap<>();
        affRepo.findAll().forEach(af -> {
            Long idEns = af.getEnseignant().getIdUtilisateur();
            counts.put(idEns, counts.getOrDefault(idEns, 0L) + 1);
        });

        List<Enseignant> ens = enseignantRepo.findAll();
        List<Map<String, Object>> out = new ArrayList<>();
        for (Enseignant e : ens) {
            Map<String, Object> m = new HashMap<>();
            m.put("idEnseignant", e.getIdUtilisateur());
            m.put("nom", e.getNom());
            m.put("prenom", e.getPrenom());
            m.put("stagesEncadres", counts.getOrDefault(e.getIdUtilisateur(), 0L));
            out.add(m);
        }
        out.sort((a, b) -> Long.compare((Long) b.get("stagesEncadres"), (Long) a.get("stagesEncadres")));
        return out;
    }
}

