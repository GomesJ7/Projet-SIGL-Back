package com.example.projet_sigl.service;

import com.example.projet_sigl.dto.ModuleDto;
import com.example.projet_sigl.entity.Enseignant;
import com.example.projet_sigl.entity.EnseignantModule;
import com.example.projet_sigl.entity.EnseignantModule.EnseignantModuleId;
import com.example.projet_sigl.entity.Module;
import com.example.projet_sigl.exception.BusinessException;
import com.example.projet_sigl.exception.DuplicateResourceException;
import com.example.projet_sigl.exception.ResourceNotFoundException;
import com.example.projet_sigl.mapper.ModuleMapper;
import com.example.projet_sigl.repository.EnseignantModuleRepository;
import com.example.projet_sigl.repository.EnseignantRepository;
import com.example.projet_sigl.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ModuleService {

    private final ModuleRepository moduleRepo;
    private final EnseignantRepository enseignantRepo;
    private final EnseignantModuleRepository emRepo;
    private static final Logger logger = LoggerFactory.getLogger(ModuleService.class);

    public List<ModuleDto> findAll() {
        return moduleRepo.findAll().stream().map(ModuleMapper::toDto).toList();
    }

    public ModuleDto findById(Long id) {
        logger.info("Fetching module with id: {}", id);
        return ModuleMapper.toDto(
                moduleRepo.findById(id).orElseThrow(() -> {
                    logger.error("Module not found with id: {}", id);
                    return ResourceNotFoundException.of("Module", id);
                })
        );
    }

    public ModuleDto create(ModuleDto dto) {
        if (moduleRepo.existsByCodeModule(dto.getCodeModule())) {
            throw new DuplicateResourceException("Code module déjà utilisé : " + dto.getCodeModule());
        }
        Module m = ModuleMapper.toEntity(dto);
        m.setIdModule(null);
        return ModuleMapper.toDto(moduleRepo.save(m));
    }

    public ModuleDto update(Long id, ModuleDto dto) {
        Module m = moduleRepo.findById(id).orElseThrow(() -> ResourceNotFoundException.of("Module", id));
        if (moduleRepo.existsByCodeModuleAndIdModuleNot(dto.getCodeModule(), id)) {
            throw new DuplicateResourceException("Code module déjà utilisé : " + dto.getCodeModule());
        }
        m.setCodeModule(dto.getCodeModule());
        m.setLibelle(dto.getLibelle());
        m.setCredits(dto.getCredits());
        return ModuleMapper.toDto(moduleRepo.save(m));
    }

    public void delete(Long id) {
        if (!moduleRepo.existsById(id)) throw ResourceNotFoundException.of("Module", id);
        try {
            moduleRepo.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new BusinessException("Suppression impossible : le module est encore affecté (enseignants/apprenants).");
        }
    }

    /** Affecter un enseignant à un module avec horodatage. */
    public void affecterEnseignant(Long idModule, Long idEnseignant) {
        Module m = moduleRepo.findById(idModule)
                .orElseThrow(() -> ResourceNotFoundException.of("Module", idModule));
        Enseignant e = enseignantRepo.findById(idEnseignant)
                .orElseThrow(() -> ResourceNotFoundException.of("Enseignant", idEnseignant));

        EnseignantModuleId pk = new EnseignantModuleId(e.getIdUtilisateur(), m.getIdModule());
        if (emRepo.existsById(pk)) return; // déjà affecté, idempotent

        EnseignantModule em = new EnseignantModule();
        em.setId(pk);
        em.setEnseignant(e);
        em.setModule(m);
        em.setDateAffectation(LocalDateTime.now());
        emRepo.save(em);
    }

    public void desaffecterEnseignant(Long idModule, Long idEnseignant) {
        EnseignantModuleId pk = new EnseignantModuleId(idEnseignant, idModule);
        if (!emRepo.existsById(pk)) throw new ResourceNotFoundException("Affectation enseignant-module introuvable");
        emRepo.deleteById(pk);
    }

    /** Récupérer les modules affectés à un enseignant. */
    public List<ModuleDto> getModulesByEnseignant(Long idEnseignant) {
        if (!enseignantRepo.existsById(idEnseignant)) throw ResourceNotFoundException.of("Enseignant", idEnseignant);
        return emRepo.findByEnseignant_IdUtilisateur(idEnseignant).stream()
                .map(em -> ModuleMapper.toDto(em.getModule()))
                .toList();
    }
}
