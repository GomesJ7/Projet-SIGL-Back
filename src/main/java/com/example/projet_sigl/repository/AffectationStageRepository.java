package com.example.projet_sigl.repository;

import com.example.projet_sigl.entity.AffectationStage;
import com.example.projet_sigl.entity.AffectationStage.AffectationStageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AffectationStageRepository extends JpaRepository<AffectationStage, AffectationStageId> {

    List<AffectationStage> findByStage_IdStage(Long idStage);

    List<AffectationStage> findByApprenant_IdUtilisateur(Long idApprenant);

    List<AffectationStage> findByEnseignant_IdUtilisateur(Long idEnseignant);
}
