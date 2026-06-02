package com.example.projet_sigl.repository;

import com.example.projet_sigl.entity.Soutenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SoutenanceRepository extends JpaRepository<Soutenance, Long> {

    Optional<Soutenance> findByStage_IdStage(Long idStage);

    @Query("""
        SELECT s FROM Soutenance s
        WHERE s.jury.idJury IN (
            SELECT je.jury.idJury FROM JuryEnseignant je
            WHERE je.enseignant.idUtilisateur = :idEnseignant
        )
    """)
    List<Soutenance> findByEnseignantJury(@Param("idEnseignant") Long idEnseignant);
}
