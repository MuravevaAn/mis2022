ALTER TABLE medical_service
    ADD COLUMN visit_id BIGINT;
ALTER TABLE medical_service
    ADD CONSTRAINT FK_medical_service_ON_visit FOREIGN KEY (visit_id) REFERENCES visit (id);