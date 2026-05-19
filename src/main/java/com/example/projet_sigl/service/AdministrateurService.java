package com.example.projet_sigl.service;

import com.example.projet_sigl.dto.AdministrateurDto;
import com.example.projet_sigl.entity.Administrateur;
import com.example.projet_sigl.enums.RoleType;
import com.example.projet_sigl.exception.DuplicateResourceException;
import com.example.projet_sigl.exception.ResourceNotFoundException;
import com.example.projet_sigl.mapper.AdministrateurMapper;
import com.example.projet_sigl.repository.AdministrateurRepository;
import com.example.projet_sigl.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdministrateurService {

    private final AdministrateurRepository adminRepo;
    private final UtilisateurRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public List<AdministrateurDto> findAll() {
        return adminRepo.findAll().stream().map(AdministrateurMapper::toDto).toList();
    }

    public AdministrateurDto findById(Long id) {
        return AdministrateurMapper.toDto(
                adminRepo.findById(id).orElseThrow(() -> ResourceNotFoundException.of("Administrateur", id))
        );
    }

    public AdministrateurDto create(AdministrateurDto dto) {
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email déjà utilisé : " + dto.getEmail());
        }
        Administrateur a = AdministrateurMapper.toEntity(dto);
        a.setIdUtilisateur(null);
        a.setRole(RoleType.ADMIN);
        a.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        a.setDateCreation(LocalDateTime.now());
        a.setActif(Boolean.TRUE);
        return AdministrateurMapper.toDto(adminRepo.save(a));
    }

    public AdministrateurDto update(Long id, AdministrateurDto dto) {
        Administrateur a = adminRepo.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Administrateur", id));
        a.setNom(dto.getNom());
        a.setPrenom(dto.getPrenom());
        a.setEmail(dto.getEmail());
        a.setNiveauAcces(dto.getNiveauAcces());
        if (dto.getActif() != null) a.setActif(dto.getActif());
        if (dto.getMotDePasse() != null && !dto.getMotDePasse().isBlank()) {
            a.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        }
        return AdministrateurMapper.toDto(adminRepo.save(a));
    }

    public void delete(Long id) {
        if (!adminRepo.existsById(id)) throw ResourceNotFoundException.of("Administrateur", id);
        adminRepo.deleteById(id);
    }
}
