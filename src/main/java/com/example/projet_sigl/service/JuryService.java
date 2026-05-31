package com.example.projet_sigl.service;

import com.example.projet_sigl.dto.JuryDto;
import com.example.projet_sigl.entity.Jury;
import com.example.projet_sigl.mapper.JuryMapper;
import com.example.projet_sigl.repository.JuryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JuryService {
    private final JuryRepository juryRepo;
    private final JuryMapper juryMapper;

    public List<JuryDto> findAll() {
        return juryRepo.findAll().stream()
                .map(juryMapper::toDto)
                .collect(Collectors.toList());
    }

    public JuryDto findById(Long id) {
        return juryRepo.findById(id)
                .map(juryMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Jury non trouvé avec l'id: " + id));
    }

    public JuryDto create(JuryDto dto) {
        Jury jury = juryMapper.toEntity(dto);
        Jury saved = juryRepo.save(jury);
        return juryMapper.toDto(saved);
    }

    public JuryDto update(Long id, JuryDto dto) {
        Jury jury = juryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Jury non trouvé avec l'id: " + id));
        jury.setNomJury(dto.getNomJury());
        Jury updated = juryRepo.save(jury);
        return juryMapper.toDto(updated);
    }

    public void delete(Long id) {
        juryRepo.deleteById(id);
    }
}
