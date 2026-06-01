package com.example.projet_sigl.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "salle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_salle")
    private Long idSalle;

    @Column(name = "nom_salle", length = 100, nullable = false)
    private String nomSalle;

    @Column(name = "localisation", length = 255)
    private String localisation;
}

