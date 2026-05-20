package com.example.projet_sigl.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "jury")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jury {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jury")
    private Long idJury;

    @Column(name = "nom_jury", length = 100, nullable = false)
    private String nomJury;
}
