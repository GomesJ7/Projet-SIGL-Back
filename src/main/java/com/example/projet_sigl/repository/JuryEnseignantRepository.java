package com.example.projet_sigl.repository;

import com.example.projet_sigl.entity.JuryEnseignant;
import com.example.projet_sigl.entity.JuryEnseignant.JuryEnseignantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JuryEnseignantRepository extends JpaRepository<JuryEnseignant, JuryEnseignantId> {
    List<JuryEnseignant> findByJury_IdJury(Long idJury);
    List<JuryEnseignant> findByEnseignant_IdUtilisateur(Long idEnseignant);
}

