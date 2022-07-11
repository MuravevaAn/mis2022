package ru.mis2022.models.entity;


/**
 * Schedule - Расписание
 * создается врачом.
 * должно быть актуально на 28 дней вперед если врач не собирается в отпуск
 * в каждый день врач выдает 8 талонов
 * врач может записывать пациентов к себе на 28 дней вперед
 * медрегистратор записывает ко всем врачам на 28 дней вперед
 * врач может удалить день/талон если нет записанных пациентов
 * врач может брать отпуск и талоны не надо создавать в это время.
 * пациент видит 14 дней вперед для записи к врачам.
 * пациент перестает видет талоны для записи за 1 час до приема
 * если у пациента есть талон в данное отделение, он не может записываться в него
 */


public class Schedule {
    private Long id;
    private Doctor doctor;
}