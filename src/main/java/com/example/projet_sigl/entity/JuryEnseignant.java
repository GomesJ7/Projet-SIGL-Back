package com.example.projet_sigl.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "jury_enseignant")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JuryEnseignant {

    @EmbeddedId
    private JuryEnseignantId id = new JuryEnseignantId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idJury")
    @JoinColumn(name = "id_jury")
    private Jury jury;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idEnseignant")
    @JoinColumn(name = "id_enseignant")
    private Enseignant enseignant;

    @Column(name = "role_jury", length = 50)
    private String roleJury;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JuryEnseignantId implements java.io.Serializable {

        @Column(name = "id_jury")
        private Long idJury;

        @Column(name = "id_enseignant")
        private Long idEnseignant;
    }
}
