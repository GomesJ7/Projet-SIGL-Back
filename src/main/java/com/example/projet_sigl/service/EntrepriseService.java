package com.example.projet_sigl.service;

import com.example.projet_sigl.dto.EntrepriseDto;
import com.example.projet_sigl.entity.Entreprise;
import com.example.projet_sigl.exception.ResourceNotFoundException;
import com.example.projet_sigl.mapper.EntrepriseMapper;
import com.example.projet_sigl.repository.EntrepriseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EntrepriseService {

    private final EntrepriseRepository repo;

    public List<EntrepriseDto> findAll() {
        return repo.findAll().stream().map(EntrepriseMapper::toDto).toList();
    }

    public EntrepriseDto findById(Long id) {
        return EntrepriseMapper.toDto(
                repo.findById(id).orElseThrow(() -> ResourceNotFoundException.of("Entreprise", id))
        );
    }

    public EntrepriseDto create(EntrepriseDto dto) {
        Entreprise e = EntrepriseMapper.toEntity(dto);
        e.setIdEntreprise(null);
        return EntrepriseMapper.toDto(repo.save(e));
    }

    public EntrepriseDto update(Long id, EntrepriseDto dto) {
        Entreprise e = repo.findById(id).orElseThrow(() -> ResourceNotFoundException.of("Entreprise", id));
        e.setNomEntreprise(dto.getNomEntreprise());
        e.setEmailEntreprise(dto.getEmailEntreprise());
        e.setAdresseEntreprise(dto.getAdresseEntreprise());
        return EntrepriseMapper.toDto(repo.save(e));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw ResourceNotFoundException.of("Entreprise", id);
        repo.deleteById(id);
    }
}
