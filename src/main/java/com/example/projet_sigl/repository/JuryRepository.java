package com.example.projet_sigl.repository;

import com.example.projet_sigl.entity.Jury;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JuryRepository extends JpaRepository<Jury, Long> {
    boolean existsByNomJury(String nomJury);
    boolean existsByNomJuryAndIdJuryNot(String nomJury, Long idJury);
}

