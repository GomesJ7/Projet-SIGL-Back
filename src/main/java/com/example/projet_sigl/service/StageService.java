package com.example.projet_sigl.service;

import com.example.projet_sigl.dto.AffectationStageDto;
import com.example.projet_sigl.dto.StageDto;
import com.example.projet_sigl.entity.*;
import com.example.projet_sigl.entity.AffectationStage.AffectationStageId;
import com.example.projet_sigl.enums.EtatType;
import com.example.projet_sigl.exception.BusinessException;
import com.example.projet_sigl.exception.ResourceNotFoundException;
import com.example.projet_sigl.mapper.StageMapper;
import com.example.projet_sigl.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StageService {

    private final StageRepository stageRepo;
    private final EntrepriseRepository entrepriseRepo;
    private final ApprenantRepository apprenantRepo;
    private final EnseignantRepository enseignantRepo;
    private final AffectationStageRepository affRepo;

    public List<StageDto> findAll() {
        return stageRepo.findAll().stream().map(StageMapper::toDto).toList();
    }

    public StageDto findById(Long id) {
        return StageMapper.toDto(stageRepo.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Stage", id)));
    }

    public List<StageDto> findByEtat(EtatType etat) {
        return stageRepo.findByEtat(etat).stream().map(StageMapper::toDto).toList();
    }

    public StageDto create(StageDto dto) {
        Stage s = StageMapper.toEntity(dto);
        s.setIdStage(null);
        if (s.getEtat() == null) s.setEtat(EtatType.EN_COURS);
        if (dto.getIdEntreprise() != null) {
            Entreprise e = entrepriseRepo.findById(dto.getIdEntreprise())
                    .orElseThrow(() -> ResourceNotFoundException.of("Entreprise", dto.getIdEntreprise()));
            s.setEntreprise(e);
        }
        return StageMapper.toDto(stageRepo.save(s));
    }

    public StageDto update(Long id, StageDto dto) {
        Stage s = stageRepo.findById(id).orElseThrow(() -> ResourceNotFoundException.of("Stage", id));
        s.setPoste(dto.getPoste());
        s.setObjectif(dto.getObjectif());
        s.setDateDebut(dto.getDateDebut());
        s.setDateFin(dto.getDateFin());
        s.setDureeSemaines(dto.getDureeSemaines());
        if (dto.getEtat() != null) s.setEtat(dto.getEtat());
        if (dto.getIdEntreprise() != null) {
            Entreprise e = entrepriseRepo.findById(dto.getIdEntreprise())
                    .orElseThrow(() -> ResourceNotFoundException.of("Entreprise", dto.getIdEntreprise()));
            s.setEntreprise(e);
        }
        return StageMapper.toDto(stageRepo.save(s));
    }

    public void delete(Long id) {
        if (!stageRepo.existsById(id)) throw ResourceNotFoundException.of("Stage", id);
        stageRepo.deleteById(id);
    }

    /**
     * Changement d'état d'un stage.
     * Un administrateur peut fixer librement n'importe quel état.
     * Les autres rôles conservent les transitions métier historiques.
     */
    public StageDto changerEtat(Long id, EtatType nouvelEtat, boolean adminOverride) {
        Stage s = stageRepo.findById(id).orElseThrow(() -> ResourceNotFoundException.of("Stage", id));
        if (adminOverride) {
            s.setEtat(nouvelEtat);
            return StageMapper.toDto(stageRepo.save(s));
        }

        EtatType courant = s.getEtat();
        boolean ok = switch (courant == null ? EtatType.EN_COURS : courant) {
            case EN_COURS -> nouvelEtat == EtatType.TERMINE;
            case TERMINE -> nouvelEtat == EtatType.VALIDE || nouvelEtat == EtatType.REFUSE;
            case VALIDE, REFUSE -> false;
        };
        if (!ok) {
            throw new BusinessException("Transition d'état invalide : " + courant + " -> " + nouvelEtat);
        }
        s.setEtat(nouvelEtat);
        return StageMapper.toDto(stageRepo.save(s));
    }

    /** Affecter un binôme (apprenant, enseignant encadrant) à un stage. */
    public void affecter(AffectationStageDto dto) {
        Stage s = stageRepo.findById(dto.getIdStage())
                .orElseThrow(() -> ResourceNotFoundException.of("Stage", dto.getIdStage()));
        Apprenant a = apprenantRepo.findById(dto.getIdApprenant())
                .orElseThrow(() -> ResourceNotFoundException.of("Apprenant", dto.getIdApprenant()));
        Enseignant e = enseignantRepo.findById(dto.getIdEnseignant())
                .orElseThrow(() -> ResourceNotFoundException.of("Enseignant", dto.getIdEnseignant()));

        AffectationStageId pk = new AffectationStageId(s.getIdStage(), a.getIdUtilisateur(), e.getIdUtilisateur());
        if (affRepo.existsById(pk)) return; // idempotent

        AffectationStage af = new AffectationStage();
        af.setId(pk);
        af.setStage(s);
        af.setApprenant(a);
        af.setEnseignant(e);
        affRepo.save(af);
    }

    public void desaffecter(AffectationStageDto dto) {
        AffectationStageId pk = new AffectationStageId(dto.getIdStage(), dto.getIdApprenant(), dto.getIdEnseignant());
        if (!affRepo.existsById(pk)) throw new ResourceNotFoundException("Affectation introuvable");
        affRepo.deleteById(pk);
    }

    public List<AffectationStageDto> getAffectations(Long idStage) {
        return affRepo.findByStage_IdStage(idStage).stream()
                .map(af -> new AffectationStageDto(
                        af.getStage().getIdStage(),
                        af.getApprenant().getIdUtilisateur(),
                        af.getEnseignant().getIdUtilisateur()))
                .toList();
    }
}
