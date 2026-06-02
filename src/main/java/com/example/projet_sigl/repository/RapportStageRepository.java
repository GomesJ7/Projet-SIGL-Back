package com.example.projet_sigl.repository;

import com.example.projet_sigl.entity.RapportStage;
import com.example.projet_sigl.enums.StatutType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RapportStageRepository extends JpaRepository<RapportStage, Long> {

    Optional<RapportStage> findByStage_IdStage(Long idStage);

    List<RapportStage> findByApprenant_IdUtilisateur(Long idApprenant);

    List<RapportStage> findByStatut(StatutType statut);

    @Query("""
        SELECT r FROM RapportStage r
        WHERE r.stage IN (
            SELECT af.stage FROM AffectationStage af
            WHERE af.enseignant.idUtilisateur = :idEnseignant
        )
    """)
    List<RapportStage> findByEnseignantAffectations(@Param("idEnseignant") Long idEnseignant);
}
