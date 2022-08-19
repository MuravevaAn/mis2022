package ru.mis2022.models.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

/**
 * Diploma - Диплом
 */

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "diploma")
public class Diploma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String universityName;

    private Long serialNumber;

    private LocalDate dateFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_history_id")
    private PersonalHistory personalHistory;

    public Diploma(String universityName, Long serialNumber, LocalDate dateFrom) {
        this.universityName = universityName;
        this.serialNumber = serialNumber;
        this.dateFrom = dateFrom;
    }
}
