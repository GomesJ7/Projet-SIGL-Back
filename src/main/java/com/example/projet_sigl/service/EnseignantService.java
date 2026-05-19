package com.example.projet_sigl.service;

import com.example.projet_sigl.dto.EnseignantDto;
import com.example.projet_sigl.dto.ModuleDto;
import com.example.projet_sigl.dto.StageDto;
import com.example.projet_sigl.entity.Enseignant;
import com.example.projet_sigl.enums.RoleType;
import com.example.projet_sigl.exception.DuplicateResourceException;
import com.example.projet_sigl.exception.ResourceNotFoundException;
import com.example.projet_sigl.mapper.EnseignantMapper;
import com.example.projet_sigl.mapper.ModuleMapper;
import com.example.projet_sigl.mapper.StageMapper;
import com.example.projet_sigl.repository.EnseignantModuleRepository;
import com.example.projet_sigl.repository.EnseignantRepository;
import com.example.projet_sigl.repository.StageRepository;
import com.example.projet_sigl.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EnseignantService {

    private final EnseignantRepository enseignantRepo;
    private final UtilisateurRepository userRepo;
    private final EnseignantModuleRepository emRepo;
    private final StageRepository stageRepo;
    private final PasswordEncoder passwordEncoder;

    public List<EnseignantDto> findAll() {
        return enseignantRepo.findAll().stream().map(EnseignantMapper::toDto).toList();
    }

    public EnseignantDto findById(Long id) {
        return EnseignantMapper.toDto(
                enseignantRepo.findById(id).orElseThrow(() -> ResourceNotFoundException.of("Enseignant", id))
        );
    }

    public EnseignantDto create(EnseignantDto dto) {
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email déjà utilisé : " + dto.getEmail());
        }
        Enseignant e = EnseignantMapper.toEntity(dto);
        e.setIdUtilisateur(null);
        e.setRole(RoleType.ENSEIGNANT);
        e.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        return EnseignantMapper.toDto(enseignantRepo.save(e));
    }

    public EnseignantDto update(Long id, EnseignantDto dto) {
        Enseignant e = enseignantRepo.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Enseignant", id));
        e.setNom(dto.getNom());
        e.setPrenom(dto.getPrenom());
        e.setEmail(dto.getEmail());
        e.setSpecialite(dto.getSpecialite());
        if (dto.getMotDePasse() != null && !dto.getMotDePasse().isBlank()) {
            e.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        }
        return EnseignantMapper.toDto(enseignantRepo.save(e));
    }

    public void delete(Long id) {
        if (!enseignantRepo.existsById(id)) throw ResourceNotFoundException.of("Enseignant", id);
        enseignantRepo.deleteById(id);
    }

    /** Modules affectés à l'enseignant. */
    public List<ModuleDto> getModules(Long idEnseignant) {
        return emRepo.findByEnseignant_IdUtilisateur(idEnseignant).stream()
                .map(em -> ModuleMapper.toDto(em.getModule()))
                .toList();
    }

    /** Stages encadrés par l'enseignant. */
    public List<StageDto> getStagesEncadres(Long idEnseignant) {
        return stageRepo.findStagesByEnseignant(idEnseignant).stream()
                .map(StageMapper::toDto)
                .toList();
    }
}
