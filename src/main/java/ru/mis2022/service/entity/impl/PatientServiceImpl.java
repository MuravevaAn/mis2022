package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.Patient;
import ru.mis2022.repositories.PatientRepository;
import ru.mis2022.service.entity.PatientService;
import ru.mis2022.service.entity.TalonService;



@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PasswordEncoder encoder;
    //todo удалить
    private final TalonService talonService;

    @Override
    public Patient findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    @Override
    //todo закрыть транзакцией
    public Patient persist(Patient patient) {
        patient.setPassword(encoder.encode(patient.getPassword()));
        return patientRepository.save(patient);
    }

    @Override
    public Patient isExistById(Long id) {
        return patientRepository.isExistById(id);
    }

}
