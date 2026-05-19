package com.example.projet_sigl.service;

import com.example.projet_sigl.dto.SoutenanceDto;
import com.example.projet_sigl.entity.Soutenance;
import com.example.projet_sigl.entity.Stage;
import com.example.projet_sigl.exception.BusinessException;
import com.example.projet_sigl.exception.ResourceNotFoundException;
import com.example.projet_sigl.mapper.SoutenanceMapper;
import com.example.projet_sigl.repository.SoutenanceRepository;
import com.example.projet_sigl.repository.StageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SoutenanceService {

    private final SoutenanceRepository soutRepo;
    private final StageRepository stageRepo;

    public List<SoutenanceDto> findAll() {
        return soutRepo.findAll().stream().map(SoutenanceMapper::toDto).toList();
    }

    public SoutenanceDto findById(Long id) {
        return SoutenanceMapper.toDto(soutRepo.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Soutenance", id)));
    }

    public SoutenanceDto planifier(SoutenanceDto dto) {
        Stage s = stageRepo.findById(dto.getIdStage())
                .orElseThrow(() -> ResourceNotFoundException.of("Stage", dto.getIdStage()));
        if (soutRepo.findByStage_IdStage(s.getIdStage()).isPresent()) {
            throw new BusinessException("Une soutenance existe déjà pour ce stage");
        }
        Soutenance sout = new Soutenance();
        sout.setStage(s);
        sout.setDateSoutenance(dto.getDateSoutenance());
        sout.setNoteFinale(dto.getNoteFinale());
        return SoutenanceMapper.toDto(soutRepo.save(sout));
    }

    public SoutenanceDto update(Long id, SoutenanceDto dto) {
        Soutenance sout = soutRepo.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Soutenance", id));
        sout.setDateSoutenance(dto.getDateSoutenance());
        sout.setNoteFinale(dto.getNoteFinale());
        return SoutenanceMapper.toDto(soutRepo.save(sout));
    }

    public void delete(Long id) {
        if (!soutRepo.existsById(id)) throw ResourceNotFoundException.of("Soutenance", id);
        soutRepo.deleteById(id);
    }
}
