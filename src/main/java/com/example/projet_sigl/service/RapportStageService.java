package com.example.projet_sigl.service;

import com.example.projet_sigl.dto.EvaluationRapportDto;
import com.example.projet_sigl.dto.RapportStageDto;
import com.example.projet_sigl.entity.Apprenant;
import com.example.projet_sigl.entity.RapportStage;
import com.example.projet_sigl.entity.Stage;
import com.example.projet_sigl.enums.StatutType;
import com.example.projet_sigl.exception.BusinessException;
import com.example.projet_sigl.exception.ResourceNotFoundException;
import com.example.projet_sigl.mapper.RapportStageMapper;
import com.example.projet_sigl.repository.ApprenantRepository;
import com.example.projet_sigl.repository.RapportStageRepository;
import com.example.projet_sigl.repository.StageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RapportStageService {

    private final RapportStageRepository rapportRepo;
    private final StageRepository stageRepo;
    private final ApprenantRepository apprenantRepo;
    private final FileStorageService fileStorage;

    public List<RapportStageDto> findAll() {
        return rapportRepo.findAll().stream().map(RapportStageMapper::toDto).toList();
    }

    public RapportStageDto findById(Long id) {
        return RapportStageMapper.toDto(rapportRepo.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Rapport", id)));
    }

    public List<RapportStageDto> findByApprenant(Long idApprenant) {
        return rapportRepo.findByApprenant_IdUtilisateur(idApprenant).stream()
                .map(RapportStageMapper::toDto).toList();
    }

    public List<RapportStageDto> findByStatut(StatutType statut) {
        return rapportRepo.findByStatut(statut).stream().map(RapportStageMapper::toDto).toList();
    }

    public List<RapportStageDto> findByEnseignantAffectations(Long idEnseignant) {
        return rapportRepo.findByEnseignantAffectations(idEnseignant).stream()
                .map(RapportStageMapper::toDto)
                .toList();
    }

    /**
     * Dépôt d'un rapport : sauvegarde du PDF + création du RapportStage en statut EN_ATTENTE.
     * Contrainte SQL : un seul rapport par stage (id_stage UNIQUE).
     */
    public RapportStageDto deposer(Long idStage, Long idApprenant, MultipartFile pdf, String titre, String versionRapport) {
        Stage s = stageRepo.findById(idStage)
                .orElseThrow(() -> ResourceNotFoundException.of("Stage", idStage));
        Apprenant a = apprenantRepo.findById(idApprenant)
                .orElseThrow(() -> ResourceNotFoundException.of("Apprenant", idApprenant));
        if (rapportRepo.findByStage_IdStage(idStage).isPresent()) {
            throw new BusinessException("Un rapport existe déjà pour ce stage");
        }
        RapportStage r = new RapportStage();
        r.setStage(s);
        r.setApprenant(a);
        r.setTitre((titre == null || titre.isBlank()) ? "Rapport de stage" : titre.trim());
        r.setFichierPath(pdf.getOriginalFilename() == null ? "rapport.pdf" : pdf.getOriginalFilename());
        try {
            r.setFichier(pdf.getBytes());
        } catch (IOException e) {
            throw new BusinessException("Impossible de lire le PDF fourni");
        }
        r.setNomFichier(pdf.getOriginalFilename() == null ? "rapport.pdf" : pdf.getOriginalFilename());
        r.setVersionRapport((versionRapport == null || versionRapport.isBlank()) ? "v1" : versionRapport.trim());
        r.setDateDepot(LocalDateTime.now());
        r.setStatut(StatutType.EN_ATTENTE);
        try {
            return RapportStageMapper.toDto(rapportRepo.save(r));
        } catch (DataAccessException ex) {
            String msg = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();
            if (msg != null && msg.contains("Packet for query is too large")) {
                throw new BusinessException("Le PDF depasse la limite actuelle MySQL (max_allowed_packet). Augmentez cette variable serveur ou reduisez la taille du PDF.");
            }
            throw ex;
        }
    }

    /** Évaluation par un enseignant : note + commentaire (le statut reste EN_ATTENTE). */
    public RapportStageDto evaluer(Long idRapport, EvaluationRapportDto dto) {
        RapportStage r = rapportRepo.findById(idRapport)
                .orElseThrow(() -> ResourceNotFoundException.of("Rapport", idRapport));
        r.setNote(dto.getNote());
        r.setCommentaire(dto.getCommentaire());
        return RapportStageMapper.toDto(rapportRepo.save(r));
    }

    public RapportStageDto valider(Long idRapport) {
        return changerStatut(idRapport, StatutType.VALIDE);
    }

    public RapportStageDto rejeter(Long idRapport) {
        return changerStatut(idRapport, StatutType.REFUSE);
    }

    private RapportStageDto changerStatut(Long idRapport, StatutType statut) {
        RapportStage r = rapportRepo.findById(idRapport)
                .orElseThrow(() -> ResourceNotFoundException.of("Rapport", idRapport));
        r.setStatut(statut);
        return RapportStageMapper.toDto(rapportRepo.save(r));
    }

    public Resource telecharger(Long idRapport) {
        RapportStage r = rapportRepo.findById(idRapport)
                .orElseThrow(() -> ResourceNotFoundException.of("Rapport", idRapport));
        if (r.getFichier() == null || r.getFichier().length == 0) throw new BusinessException("Aucun fichier associé");
        return new ByteArrayResource(r.getFichier()) {
            @Override
            public String getFilename() {
                return r.getNomFichier() != null ? r.getNomFichier() : "rapport.pdf";
            }
        };
    }

    public void delete(Long id) {
        RapportStage r = rapportRepo.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Rapport", id));
        rapportRepo.delete(r);
    }
}
