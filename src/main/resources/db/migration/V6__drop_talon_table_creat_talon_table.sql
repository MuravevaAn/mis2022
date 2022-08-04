DROP TABLE talon CASCADE;
CREATE TABLE talon
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    time       TIMESTAMP,
    patient_id BIGINT,
    doctor_id  BIGINT
);

