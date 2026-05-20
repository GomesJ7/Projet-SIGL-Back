package com.example.projet_sigl.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "module")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_module")
    private Long idModule;

    @Column(name = "code_module", length = 20, nullable = false, unique = true)
    private String codeModule;

    @Column(name = "libelle", length = 100, nullable = false)
    private String libelle;

    @Column(name = "credits")
    private Integer credits;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EnseignantModule> enseignantsAffectes = new HashSet<>();
}
