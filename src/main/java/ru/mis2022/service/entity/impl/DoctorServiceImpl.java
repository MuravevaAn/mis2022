package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.Department;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.repositories.DoctorRepository;
import ru.mis2022.service.entity.DoctorService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final PasswordEncoder encoder;

    @Override
    public Doctor findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    @Override
    //todo метод закрыть транзакцией
    public Doctor persist(Doctor doctor) {
        doctor.setPassword(encoder.encode(doctor.getPassword()));
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor merge(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> findByDepartment(Department department) {
        return doctorRepository.findByDepartment(department);
    }

    @Override
    //todo вырезать _ из имен
    public List<Doctor> findAllByDepartmentId(Long id) {
        return doctorRepository.findAllByDepartmentId(id);
    }

    @Override
    public Doctor existById(Long id) {
        return doctorRepository.existById(id);
    }

}
