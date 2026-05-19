package com.example.projet_sigl.repository;

import com.example.projet_sigl.entity.EnseignantModule;
import com.example.projet_sigl.entity.EnseignantModule.EnseignantModuleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnseignantModuleRepository extends JpaRepository<EnseignantModule, EnseignantModuleId> {

    List<EnseignantModule> findByEnseignant_IdUtilisateur(Long idEnseignant);

    List<EnseignantModule> findByModule_IdModule(Long idModule);
}
