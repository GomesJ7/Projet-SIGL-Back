package com.example.projet_sigl.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "filiere")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Filiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_filiere")
    private Long idFiliere;

    @Column(name = "nom_filiere", length = 100, nullable = false, unique = true)
    private String nomFiliere;
}
