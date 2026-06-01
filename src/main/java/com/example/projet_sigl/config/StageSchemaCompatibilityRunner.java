package com.example.projet_sigl.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Harmonise les anciennes structures SQL avec le mapping JPA actuel des stages.
 * Certains dumps legacy imposent encore des colonnes NOT NULL (id_apprenant, id_encadrant,
 * id_entreprise, date_fin) alors que la logique métier actuelle les gère via affectation_stage
 * et des champs optionnels.
 */
@Component
@RequiredArgsConstructor
public class StageSchemaCompatibilityRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(StageSchemaCompatibilityRunner.class);

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        makeNullableIfRequired("id_apprenant", "BIGINT");
        makeNullableIfRequired("id_encadrant", "BIGINT");
        makeNullableIfRequired("id_entreprise", "BIGINT");
        makeNullableIfRequired("date_fin", "DATE");
    }

    private void makeNullableIfRequired(String columnName, String sqlType) {
        try {
            String query = """
                SELECT IS_NULLABLE
                FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA = DATABASE()
                  AND TABLE_NAME = 'stage'
                  AND COLUMN_NAME = ?
                """;

            String isNullable = jdbcTemplate.query(
                    query,
                    rs -> rs.next() ? rs.getString("IS_NULLABLE") : null,
                    columnName
            );

            if ("NO".equalsIgnoreCase(isNullable)) {
                String alter = "ALTER TABLE stage MODIFY COLUMN " + columnName + " " + sqlType + " NULL";
                jdbcTemplate.execute(alter);
                log.warn("Compatibilite schema: colonne stage.{} basculee en NULL.", columnName);
            }
        } catch (Exception ex) {
            log.warn("Compatibilite schema: impossible d'ajuster stage.{} ({})", columnName, ex.getMessage());
        }
    }
}

