package com.example.projet_sigl.repository;

import com.example.projet_sigl.entity.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FiliereRepository extends JpaRepository<Filiere, Long> {
    boolean existsByNomFiliere(String nomFiliere);
}

