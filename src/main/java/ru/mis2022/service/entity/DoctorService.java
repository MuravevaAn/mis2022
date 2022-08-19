package ru.mis2022.service.entity;

import ru.mis2022.models.entity.Department;
import ru.mis2022.models.entity.Doctor;

import java.util.List;


public interface DoctorService {

    Doctor findByEmail(String email);

    Doctor persist(Doctor doctor);
    Doctor merge(Doctor doctor);

    List<Doctor> findByDepartment(Department department);

    List<Doctor> findAllByDepartmentId(Long id);

    Doctor existById(Long id);

}
