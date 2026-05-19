package com.example.projet_sigl.repository;

import com.example.projet_sigl.entity.Stage;
import com.example.projet_sigl.enums.EtatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {

    List<Stage> findByEtat(EtatType etat);

    List<Stage> findByEntreprise_IdEntreprise(Long idEntreprise);

    /** Historique de stages d'un apprenant via la relation ternaire AffectationStage. */
    @Query("SELECT DISTINCT a.stage FROM AffectationStage a WHERE a.apprenant.idUtilisateur = :idApprenant")
    List<Stage> findStagesByApprenant(@Param("idApprenant") Long idApprenant);

    /** Stages encadrés par un enseignant. */
    @Query("SELECT DISTINCT a.stage FROM AffectationStage a WHERE a.enseignant.idUtilisateur = :idEnseignant")
    List<Stage> findStagesByEnseignant(@Param("idEnseignant") Long idEnseignant);
}
