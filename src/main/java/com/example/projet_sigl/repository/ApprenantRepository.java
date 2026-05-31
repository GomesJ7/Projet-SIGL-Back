package com.example.projet_sigl.repository;

import com.example.projet_sigl.entity.Apprenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprenantRepository extends JpaRepository<Apprenant, Long> {

    Optional<Apprenant> findByEmail(String email);

    Optional<Apprenant> findByMatricule(String matricule);

    boolean existsByMatricule(String matricule);

    List<Apprenant> findByPromotion_IdPromotion(Long idPromotion);

    long countByFiliere_IdFiliere(Long idFiliere);
}
