package com.example.projet_sigl.service;

import com.example.projet_sigl.dto.JuryDto;
import com.example.projet_sigl.dto.JuryEnseignantDto;
import com.example.projet_sigl.entity.Enseignant;
import com.example.projet_sigl.entity.Jury;
import com.example.projet_sigl.entity.JuryEnseignant;
import com.example.projet_sigl.entity.JuryEnseignant.JuryEnseignantId;
import com.example.projet_sigl.exception.DuplicateResourceException;
import com.example.projet_sigl.exception.ResourceNotFoundException;
import com.example.projet_sigl.mapper.JuryMapper;
import com.example.projet_sigl.repository.EnseignantRepository;
import com.example.projet_sigl.repository.JuryEnseignantRepository;
import com.example.projet_sigl.repository.JuryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class JuryService {

    private final JuryRepository juryRepo;
    private final EnseignantRepository enseignantRepo;
    private final JuryEnseignantRepository juryEnseignantRepo;

    public List<JuryDto> findAll() {
        return juryRepo.findAll().stream().map(JuryMapper::toDto).toList();
    }

    public JuryDto findById(Long id) {
        Jury jury = juryRepo.findById(id).orElseThrow(() -> ResourceNotFoundException.of("Jury", id));
        return JuryMapper.toDto(jury);
    }

    public JuryDto create(JuryDto dto) {
        if (juryRepo.existsByNomJury(dto.getNomJury())) {
            throw new DuplicateResourceException("Nom de jury déjà utilisé : " + dto.getNomJury());
        }
        Jury jury = JuryMapper.toEntity(dto);
        jury.setIdJury(null);
        return JuryMapper.toDto(juryRepo.save(jury));
    }

    public JuryDto update(Long id, JuryDto dto) {
        Jury jury = juryRepo.findById(id).orElseThrow(() -> ResourceNotFoundException.of("Jury", id));
        if (juryRepo.existsByNomJuryAndIdJuryNot(dto.getNomJury(), id)) {
            throw new DuplicateResourceException("Nom de jury déjà utilisé : " + dto.getNomJury());
        }
        jury.setNomJury(dto.getNomJury());
        return JuryMapper.toDto(juryRepo.save(jury));
    }

    public void delete(Long id) {
        if (!juryRepo.existsById(id)) throw ResourceNotFoundException.of("Jury", id);
        juryRepo.deleteById(id);
    }

    public void affecterEnseignant(Long idJury, Long idEnseignant, String roleJury) {
        Jury jury = juryRepo.findById(idJury).orElseThrow(() -> ResourceNotFoundException.of("Jury", idJury));
        Enseignant enseignant = enseignantRepo.findById(idEnseignant)
                .orElseThrow(() -> ResourceNotFoundException.of("Enseignant", idEnseignant));

        JuryEnseignantId pk = new JuryEnseignantId(idJury, idEnseignant);
        JuryEnseignant affectation = juryEnseignantRepo.findById(pk).orElseGet(() -> {
            JuryEnseignant je = new JuryEnseignant();
            je.setId(pk);
            je.setJury(jury);
            je.setEnseignant(enseignant);
            return je;
        });
        affectation.setRoleJury((roleJury == null || roleJury.isBlank()) ? null : roleJury.trim());
        juryEnseignantRepo.save(affectation);
    }

    public void desaffecterEnseignant(Long idJury, Long idEnseignant) {
        JuryEnseignantId pk = new JuryEnseignantId(idJury, idEnseignant);
        if (!juryEnseignantRepo.existsById(pk)) {
            throw new ResourceNotFoundException("Affectation jury-enseignant introuvable");
        }
        juryEnseignantRepo.deleteById(pk);
    }

    public List<JuryEnseignantDto> getEnseignants(Long idJury) {
        if (!juryRepo.existsById(idJury)) throw ResourceNotFoundException.of("Jury", idJury);
        return juryEnseignantRepo.findByJury_IdJury(idJury).stream()
                .map(je -> new JuryEnseignantDto(
                        je.getJury().getIdJury(),
                        je.getJury().getNomJury(),
                        je.getEnseignant().getIdUtilisateur(),
                        je.getEnseignant().getNom(),
                        je.getEnseignant().getPrenom(),
                        je.getRoleJury()
                ))
                .toList();
    }

    public List<JuryEnseignantDto> getJuriesByEnseignant(Long idEnseignant) {
        if (!enseignantRepo.existsById(idEnseignant)) throw ResourceNotFoundException.of("Enseignant", idEnseignant);
        return juryEnseignantRepo.findByEnseignant_IdUtilisateur(idEnseignant).stream()
                .map(je -> new JuryEnseignantDto(
                        je.getJury().getIdJury(),
                        je.getJury().getNomJury(),
                        je.getEnseignant().getIdUtilisateur(),
                        je.getEnseignant().getNom(),
                        je.getEnseignant().getPrenom(),
                        je.getRoleJury()
                ))
                .toList();
    }
}

