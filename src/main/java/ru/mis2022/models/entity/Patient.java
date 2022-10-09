package ru.mis2022.models.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.lang.Nullable;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
/**
    Patient - Пациент.
    может получать лечение у врачей
    может записываться на лечение на 14 дней вперед
    может видеть свои талоны к врачам
    может видеть свои обращения/посещения
 */

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Patient extends User {

    private String passport;

    private String polis;

    @Basic
    private String snils;

    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    private List<Talon> talons;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    private Set<Appeal> appeals;

    public Patient(String email, String password, String firstName, String lastName, @Nullable String surname,
                   LocalDate birthday, Role role, String passport, String polis, String snils, String address) {
        super(email, password, firstName, lastName, surname, birthday, role);
        this.passport = passport;
        this.polis = polis;
        this.snils = snils;
        this.address = address;
    }

}
