package com.example.projet_sigl.service;

import com.example.projet_sigl.dto.ApprenantDto;
import com.example.projet_sigl.dto.StageDto;
import com.example.projet_sigl.entity.Apprenant;
import com.example.projet_sigl.entity.Promotion;
import com.example.projet_sigl.enums.RoleType;
import com.example.projet_sigl.exception.DuplicateResourceException;
import com.example.projet_sigl.exception.ResourceNotFoundException;
import com.example.projet_sigl.mapper.ApprenantMapper;
import com.example.projet_sigl.mapper.StageMapper;
import com.example.projet_sigl.repository.ApprenantRepository;
import com.example.projet_sigl.repository.PromotionRepository;
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
public class ApprenantService {

    private final ApprenantRepository apprenantRepo;
    private final UtilisateurRepository userRepo;
    private final PromotionRepository promotionRepo;
    private final StageRepository stageRepo;
    private final PasswordEncoder passwordEncoder;

    public List<ApprenantDto> findAll() {
        return apprenantRepo.findAll().stream().map(ApprenantMapper::toDto).toList();
    }

    public ApprenantDto findById(Long id) {
        return ApprenantMapper.toDto(
                apprenantRepo.findById(id).orElseThrow(() -> ResourceNotFoundException.of("Apprenant", id))
        );
    }

    public ApprenantDto create(ApprenantDto dto) {
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email déjà utilisé : " + dto.getEmail());
        }
        if (dto.getMatricule() != null && apprenantRepo.existsByMatricule(dto.getMatricule())) {
            throw new DuplicateResourceException("Matricule déjà utilisé : " + dto.getMatricule());
        }
        Apprenant a = ApprenantMapper.toEntity(dto);
        a.setIdUtilisateur(null);
        a.setRole(RoleType.APPRENANT);
        a.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));

        if (dto.getIdPromotion() != null) {
            Promotion p = promotionRepo.findById(dto.getIdPromotion())
                    .orElseThrow(() -> ResourceNotFoundException.of("Promotion", dto.getIdPromotion()));
            a.setPromotion(p);
        }

        return ApprenantMapper.toDto(apprenantRepo.save(a));
    }

    public ApprenantDto update(Long id, ApprenantDto dto) {
        Apprenant a = apprenantRepo.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Apprenant", id));
        a.setNom(dto.getNom());
        a.setPrenom(dto.getPrenom());
        a.setEmail(dto.getEmail());
        a.setMatricule(dto.getMatricule());
        a.setNiveau(dto.getNiveau());
        a.setDateNaissance(dto.getDateNaissance());
        if (dto.getMotDePasse() != null && !dto.getMotDePasse().isBlank()) {
            a.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        }
        if (dto.getIdPromotion() != null) {
            Promotion p = promotionRepo.findById(dto.getIdPromotion())
                    .orElseThrow(() -> ResourceNotFoundException.of("Promotion", dto.getIdPromotion()));
            a.setPromotion(p);
        }
        return ApprenantMapper.toDto(apprenantRepo.save(a));
    }

    public void delete(Long id) {
        if (!apprenantRepo.existsById(id)) throw ResourceNotFoundException.of("Apprenant", id);
        apprenantRepo.deleteById(id);
    }

    /** Affectation explicite à une promotion (suivi académique). */
    public ApprenantDto affecterPromotion(Long idApprenant, Long idPromotion) {
        Apprenant a = apprenantRepo.findById(idApprenant)
                .orElseThrow(() -> ResourceNotFoundException.of("Apprenant", idApprenant));
        Promotion p = promotionRepo.findById(idPromotion)
                .orElseThrow(() -> ResourceNotFoundException.of("Promotion", idPromotion));
        a.setPromotion(p);
        return ApprenantMapper.toDto(apprenantRepo.save(a));
    }

    /** Historique des stages d'un apprenant. */
    public List<StageDto> getHistoriqueStages(Long idApprenant) {
        return stageRepo.findStagesByApprenant(idApprenant).stream()
                .map(StageMapper::toDto)
                .toList();
    }

    public List<ApprenantDto> findByPromotion(Long idPromotion) {
        return apprenantRepo.findByPromotion_IdPromotion(idPromotion).stream()
                .map(ApprenantMapper::toDto)
                .toList();
    }
}
