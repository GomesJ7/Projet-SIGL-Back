package com.example.projet_sigl.repository;

import com.example.projet_sigl.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    boolean existsByCodeModule(String codeModule);

    boolean existsByCodeModuleAndIdModuleNot(String codeModule, Long idModule);
}
