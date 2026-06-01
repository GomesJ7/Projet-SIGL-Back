-- ==============================
-- CREATE SALLE TABLE
-- ==============================

CREATE TABLE IF NOT EXISTS salle (
    id_salle BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nom_salle VARCHAR(100) NOT NULL,
    localisation VARCHAR(255)
);

-- ==============================
-- EXTEND SOUTENANCE TABLE
-- ==============================

ALTER TABLE soutenance
ADD COLUMN IF NOT EXISTS id_salle BIGINT,
ADD COLUMN IF NOT EXISTS id_jury BIGINT,
ADD COLUMN IF NOT EXISTS observation TEXT;

ALTER TABLE soutenance
ADD CONSTRAINT fk_soutenance_salle FOREIGN KEY (id_salle) REFERENCES salle(id_salle) ON DELETE SET NULL,
ADD CONSTRAINT fk_soutenance_jury FOREIGN KEY (id_jury) REFERENCES jury(id_jury) ON DELETE SET NULL;

