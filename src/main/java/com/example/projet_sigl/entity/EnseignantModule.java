package com.example.projet_sigl.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Table d'association porteuse : enseignant <-> module avec date d'affectation.
 * Correspond à la table SQL `enseignant_module` avec PRIMARY KEY (id_enseignant, id_module).
 * Une entité dédiée est nécessaire (pas un simple @ManyToMany) car la table porte
 * un attribut supplémentaire `date_affectation`.
 */
@Entity
@Table(name = "enseignant_module")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnseignantModule {

    @EmbeddedId
    private EnseignantModuleId id = new EnseignantModuleId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idEnseignant")
    @JoinColumn(name = "id_enseignant")
    private Enseignant enseignant;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idModule")
    @JoinColumn(name = "id_module")
    private Module module;

    @Column(name = "date_affectation")
    private LocalDateTime dateAffectation;

    /** PK composite. */
    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnseignantModuleId implements Serializable {

        @Column(name = "id_enseignant")
        private Long idEnseignant;

        @Column(name = "id_module")
        private Long idModule;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof EnseignantModuleId that)) return false;
            return Objects.equals(idEnseignant, that.idEnseignant)
                    && Objects.equals(idModule, that.idModule);
        }

        @Override
        public int hashCode() {
            return Objects.hash(idEnseignant, idModule);
        }
    }
}
