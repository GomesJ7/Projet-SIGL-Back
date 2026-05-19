package com.example.projet_sigl.repository;

import com.example.projet_sigl.entity.Soutenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoutenanceRepository extends JpaRepository<Soutenance, Long> {

    Optional<Soutenance> findByStage_IdStage(Long idStage);
}
