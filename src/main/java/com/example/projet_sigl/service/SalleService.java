package com.example.projet_sigl.service;

import com.example.projet_sigl.dto.SalleDto;
import com.example.projet_sigl.entity.Salle;
import com.example.projet_sigl.exception.BusinessException;
import com.example.projet_sigl.exception.ResourceNotFoundException;
import com.example.projet_sigl.mapper.SalleMapper;
import com.example.projet_sigl.repository.SalleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SalleService {

    private final SalleRepository salleRepository;

    public List<SalleDto> findAll() {
        return salleRepository.findAll().stream().map(SalleMapper::toDto).toList();
    }

    public SalleDto findById(Long id) {
        return SalleMapper.toDto(salleRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Salle", id)));
    }

    public SalleDto create(SalleDto dto) {
        if (dto.getNomSalle() == null || dto.getNomSalle().trim().isEmpty()) {
            throw new BusinessException("Le nom de la salle est obligatoire");
        }
        Salle salle = new Salle();
        salle.setNomSalle(dto.getNomSalle().trim());
        salle.setLocalisation(dto.getLocalisation());
        return SalleMapper.toDto(salleRepository.save(salle));
    }

    public SalleDto update(Long id, SalleDto dto) {
        Salle salle = salleRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Salle", id));
        if (dto.getNomSalle() != null && !dto.getNomSalle().trim().isEmpty()) {
            salle.setNomSalle(dto.getNomSalle().trim());
        }
        if (dto.getLocalisation() != null) {
            salle.setLocalisation(dto.getLocalisation());
        }
        return SalleMapper.toDto(salleRepository.save(salle));
    }

    public void delete(Long id) {
        if (!salleRepository.existsById(id)) {
            throw ResourceNotFoundException.of("Salle", id);
        }
        salleRepository.deleteById(id);
    }
}

