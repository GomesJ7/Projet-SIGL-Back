package com.example.projet_sigl.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Relation ternaire stage <-> apprenant <-> enseignant.
 * PRIMARY KEY (id_stage, id_apprenant, id_enseignant).
 */
@Entity
@Table(name = "affectation_stage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AffectationStage {

    @EmbeddedId
    private AffectationStageId id = new AffectationStageId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idStage")
    @JoinColumn(name = "id_stage")
    private Stage stage;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idApprenant")
    @JoinColumn(name = "id_apprenant")
    private Apprenant apprenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idEnseignant")
    @JoinColumn(name = "id_enseignant")
    private Enseignant enseignant;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AffectationStageId implements Serializable {

        @Column(name = "id_stage")
        private Long idStage;

        @Column(name = "id_apprenant")
        private Long idApprenant;

        @Column(name = "id_enseignant")
        private Long idEnseignant;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof AffectationStageId that)) return false;
            return Objects.equals(idStage, that.idStage)
                    && Objects.equals(idApprenant, that.idApprenant)
                    && Objects.equals(idEnseignant, that.idEnseignant);
        }

        @Override
        public int hashCode() {
            return Objects.hash(idStage, idApprenant, idEnseignant);
        }
    }
}
