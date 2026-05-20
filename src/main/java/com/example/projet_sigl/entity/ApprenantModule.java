package com.example.projet_sigl.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * Table d'association porteuse : apprenant <-> module avec moyenne et statut de validation.
 * Correspond à la table SQL `apprenant_module` avec PRIMARY KEY (id_apprenant, id_module).
 */
@Entity
@Table(name = "apprenant_module")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApprenantModule {

    @EmbeddedId
    private ApprenantModuleId id = new ApprenantModuleId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idApprenant")
    @JoinColumn(name = "id_apprenant")
    private Apprenant apprenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idModule")
    @JoinColumn(name = "id_module")
    private Module module;

    @Column(name = "moyenne", precision = 4, scale = 2)
    @Min(value = 0, message = "La moyenne doit être supérieure ou égale à 0.")
    @Max(value = 20, message = "La moyenne doit être inférieure ou égale à 20.")
    private BigDecimal moyenne;

    @Column(name = "statut_validation")
    private Boolean statutValidation;

    /** PK composite. */
    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApprenantModuleId implements Serializable {

        @Column(name = "id_apprenant")
        private Long idApprenant;

        @Column(name = "id_module")
        private Long idModule;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ApprenantModuleId that)) return false;
            return Objects.equals(idApprenant, that.idApprenant)
                    && Objects.equals(idModule, that.idModule);
        }

        @Override
        public int hashCode() {
            return Objects.hash(idApprenant, idModule);
        }
    }
}
