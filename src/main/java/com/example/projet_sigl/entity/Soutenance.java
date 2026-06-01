package com.example.projet_sigl.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "soutenance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Soutenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_soutenance")
    private Long idSoutenance;

    @Column(name = "date_soutenance")
    private LocalDateTime dateSoutenance;

    @Column(name = "note_finale", precision = 5, scale = 2)
    private BigDecimal noteFinale;

    @Column(name = "observation", columnDefinition = "TEXT")
    private String observation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stage", unique = true)
    private Stage stage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_salle")
    private Salle salle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jury")
    private Jury jury;
}
