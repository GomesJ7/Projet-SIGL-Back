package com.example.projet_sigl.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "entreprise")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entreprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entreprise")
    private Long idEntreprise;

    @Column(name = "nom_entreprise", length = 150)
    private String nomEntreprise;

    @Column(name = "email_entreprise", length = 150)
    private String emailEntreprise;

    @Column(name = "adresse_entreprise", length = 255)
    private String adresseEntreprise;
}
