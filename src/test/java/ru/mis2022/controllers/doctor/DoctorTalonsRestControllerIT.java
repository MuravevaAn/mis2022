package ru.mis2022.controllers.doctor;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.mis2022.models.entity.Department;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.Patient;
import ru.mis2022.models.entity.PersonalHistory;
import ru.mis2022.models.entity.Role;
import ru.mis2022.service.entity.DepartmentService;
import ru.mis2022.service.entity.DoctorService;
import ru.mis2022.service.entity.PatientService;
import ru.mis2022.service.entity.RoleService;
import ru.mis2022.service.entity.TalonService;
import ru.mis2022.util.ContextIT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class DoctorTalonsRestControllerIT extends ContextIT {

    @Autowired TalonService talonService;
    @Autowired DoctorService doctorService;
    @Autowired RoleService roleService;
    @Autowired DepartmentService departmentService;
    @Autowired PatientService patientService;

    Role initRole(String name) {
        return roleService.persist(Role.builder()
                .name(name)
                .build());
    }

    Department initDepartment(String name) {
        return departmentService.persist(Department.builder()
                .name(name)
                .build());
    }

    Doctor initDoctor(Role role, Department department, PersonalHistory personalHistory, String email) {
        return doctorService.persist(new Doctor(
                email,
                String.valueOf("1"),
                "f_name",
                "l_name",
                "surname",
                LocalDate.now().minusYears(20),
                role,
                department
        ));
    }
    Patient initPatient(Role role) {
        return patientService.persist(new Patient(
                "patient1@email.com",
                String.valueOf("1"),
                "f_name",
                "l_name",
                "surname",
                LocalDate.now().minusYears(20),
                role,
                "passport",
                "polis",
                "snils",
                "address"));
    }

    private String formatDate(LocalDate date, int hour) {
        LocalDateTime time = LocalDateTime.of(date, LocalTime.of(8, 0).plusHours(hour));
        return time.format(DATE_TIME_FORMATTER);
    }

    @Test
    public void addTalonTest() throws Exception {

        Role role = initRole("DOCTOR");
        Role role1 = initRole("PATIENT");
        Department department = initDepartment("Therapy");
        Doctor doctor1 = initDoctor(role, department, null, "doctor@email.com");
        Patient patient = initPatient(role1);

        LocalDate date = LocalDate.now();

        LocalDateTime time2 = LocalDateTime.of(LocalDate.now().plusDays(13), LocalTime.of(8, 0).plusHours(3));
        String foramttedString2 = time2.format(DATE_TIME_FORMATTER);

        accessToken = tokenUtil.obtainNewAccessToken(doctor1.getEmail(), "1", mockMvc);

        mockMvc.perform(post("/api/doctor/talon/add")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data[0].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data[0].time", Is.is(formatDate(date, 0))))
                .andExpect(jsonPath("$.data[0].doctorId", Is.is(doctor1.getId().intValue())))

                .andExpect(jsonPath("$.data[1].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data[1].time", Is.is(formatDate(date, 1))))
                .andExpect(jsonPath("$.data[1].doctorId", Is.is(doctor1.getId().intValue())))

                .andExpect(jsonPath("$.data[2].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data[2].time", Is.is(formatDate(date, 2))))
                .andExpect(jsonPath("$.data[2].doctorId", Is.is(doctor1.getId().intValue())))

                .andExpect(jsonPath("$.data[3].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data[3].time", Is.is(formatDate(date, 3))))
                .andExpect(jsonPath("$.data[3].doctorId", Is.is(doctor1.getId().intValue())))

                .andExpect(jsonPath("$.data[55].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data[55].time", Is.is(foramttedString2)))
                .andExpect(jsonPath("$.data[55].doctorId", Is.is(doctor1.getId().intValue())));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));


        mockMvc.perform(post("/api/doctor/talon/add")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(401)))
                .andExpect(jsonPath("$.text", Is.is("У доктора есть талоны на данные дни")));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));
    }
}
