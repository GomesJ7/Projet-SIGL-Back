package com.example.projet_sigl.service;

import com.example.projet_sigl.dto.FiliereDto;
import com.example.projet_sigl.entity.Filiere;
import com.example.projet_sigl.exception.BusinessException;
import com.example.projet_sigl.exception.DuplicateResourceException;
import com.example.projet_sigl.exception.ResourceNotFoundException;
import com.example.projet_sigl.mapper.FiliereMapper;
import com.example.projet_sigl.repository.ApprenantRepository;
import com.example.projet_sigl.repository.FiliereRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FiliereService {

    private final FiliereRepository filiereRepo;
    private final ApprenantRepository apprenantRepo;

    public List<FiliereDto> findAll() {
        return filiereRepo.findAll().stream().map(FiliereMapper::toDto).toList();
    }

    public FiliereDto create(FiliereDto dto) {
        if (filiereRepo.existsByNomFiliere(dto.getNomFiliere())) {
            throw new DuplicateResourceException("Filière déjà existante : " + dto.getNomFiliere());
        }
        Filiere f = FiliereMapper.toEntity(dto);
        f.setIdFiliere(null);
        return FiliereMapper.toDto(filiereRepo.save(f));
    }

    public FiliereDto update(Long id, FiliereDto dto) {
        Filiere f = filiereRepo.findById(id).orElseThrow(() -> ResourceNotFoundException.of("Filière", id));
        boolean sameName = f.getNomFiliere() != null && f.getNomFiliere().equalsIgnoreCase(dto.getNomFiliere());
        if (!sameName && filiereRepo.existsByNomFiliere(dto.getNomFiliere())) {
            throw new DuplicateResourceException("Filière déjà existante : " + dto.getNomFiliere());
        }
        f.setNomFiliere(dto.getNomFiliere());
        return FiliereMapper.toDto(filiereRepo.save(f));
    }

    public void delete(Long id) {
        if (!filiereRepo.existsById(id)) throw ResourceNotFoundException.of("Filière", id);

        long usedByApprenants = apprenantRepo.countByFiliere_IdFiliere(id);
        if (usedByApprenants > 0) {
            throw new BusinessException("Suppression impossible : cette filière est affectée à " + usedByApprenants + " apprenant(s)");
        }

        try {
            filiereRepo.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new BusinessException("Suppression impossible : la filière est référencée par d'autres données.");
        }
    }
}
