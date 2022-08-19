package ru.mis2022.models.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * Attestation - Aттестация
 */

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "attestation")
public class Attestation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    private String documentNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_history_id")
    private PersonalHistory personalHistory;

    public Attestation(LocalDate dateFrom, LocalDate dateTo, String documentNumber) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.documentNumber = documentNumber;
    }
}
