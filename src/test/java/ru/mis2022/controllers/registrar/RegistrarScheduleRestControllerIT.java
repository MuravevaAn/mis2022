package ru.mis2022.controllers.registrar;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.mis2022.models.entity.Department;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.MedicalOrganization;
import ru.mis2022.models.entity.Patient;
import ru.mis2022.models.entity.PersonalHistory;
import ru.mis2022.models.entity.Registrar;
import ru.mis2022.models.entity.Role;
import ru.mis2022.service.entity.DepartmentService;
import ru.mis2022.service.entity.DoctorService;
import ru.mis2022.service.entity.MedicalOrganizationService;
import ru.mis2022.service.entity.PatientService;
import ru.mis2022.service.entity.RegistrarService;
import ru.mis2022.service.entity.RoleService;
import ru.mis2022.service.entity.TalonService;
import ru.mis2022.util.ContextIT;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class RegistrarScheduleRestControllerIT extends ContextIT {

    @Autowired
    RoleService roleService;

    @Autowired
    RegistrarService registrarService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    MedicalOrganizationService medicalOrganizationService;

    @Autowired
    TalonService talonService;
    @Autowired
    PatientService patientService;

    Role initRole(String name) {
        return roleService.persist(Role.builder()
                .name(name)
                .build());
    }

    Registrar initRegistrar(Role role) {
        return registrarService.persist(new Registrar(
                "registrar1@email.com",
                String.valueOf("1"),
                "f_name",
                "l_name",
                "surname",
                LocalDate.now().minusYears(20),
                role
        ));
    }

    Department initDepartment(String name, MedicalOrganization medicalOrganization) {
        return departmentService.persist(Department.builder()
                .name(name)
                .medicalOrganization(medicalOrganization)
                .build());
    }

    Doctor initDoctor(Role role, Department department, PersonalHistory personalHistory) {
        return doctorService.persist(new Doctor(
                "doctor1@email.com",
                String.valueOf("1"),
                "f_name",
                "l_name",
                "surname",
                LocalDate.now().minusYears(20),
                role,
                department
        ));
    }

    MedicalOrganization initMedicalOrganizations(String name, String address) {
        return medicalOrganizationService.persist(MedicalOrganization.builder()
                .name(name)
                .address(address)
                .build());
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

    @Test
    public void getAllMedicalOrganizationsTest() throws Exception {

        Role roleRegistrar = initRole("REGISTRAR");
        Registrar registrar = initRegistrar(roleRegistrar);

        accessToken = tokenUtil.obtainNewAccessToken(registrar.getEmail(), "1", mockMvc);

        //?????????????????????? ?????????????????????? ?? ???????? ??????????????????????
        mockMvc.perform(get("/api/registrar/medicalOrganizations")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(414)))
                .andExpect(jsonPath("$.text", Is.is(
                        "???????????? ?????????????????????? ?????????????????????? ????????!")));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        MedicalOrganization medicalOrganization = initMedicalOrganizations(
                "City Hospital", "Moscow, Pravda street, 30");

        //?????????? ???????????? ?????????????????????? ??????????????????????
        mockMvc.perform(get("/api/registrar/medicalOrganizations")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data[0].name", Is.is("City Hospital")))
                .andExpect(jsonPath("$.data[0].address", Is.is("Moscow, Pravda street, 30")));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        //???????????? ?????????????????????????? ?????????????????????? ?????????????????????? ?? ???????????????????????????? id
        mockMvc.perform(post("/api/registrar/departments/{id}", 100)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(414)))
                .andExpect(jsonPath("$.text", Is.is(
                        "?????????????????????? ?????????????????????? ?? ?????????? id ??????!")));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        //???????????????????????? ?? ?????????????????????? ?????????????????????? ??????????????????????
        mockMvc.perform(post("/api/registrar/departments/{id}",medicalOrganization.getId())
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(415)))
                .andExpect(jsonPath("$.text", Is.is(
                        "?? ?????????????????????? ?????????????????????? ?????? ??????????????????????????!")));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        Department department = initDepartment("Therapy", medicalOrganization);

        //?????????? ???????????? ??????????????????????????
        mockMvc.perform(post("/api/registrar/departments/{id}", medicalOrganization.getId())
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data[0].name", Is.is("Therapy")));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        //???????????????????????? ?? ?????????? id ??????
        mockMvc.perform(post("/api/registrar/doctors/{id}", 100)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(414)))
                .andExpect(jsonPath("$.text", Is.is(
                        "???????????????????????? ?? ?????????? id ??????!")));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        //?? ???????????????????????? ?????? ????????????????
        mockMvc.perform(post("/api/registrar/doctors/{id}", department.getId())
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(415)))
                .andExpect(jsonPath("$.text", Is.is(
                        "?? ???????????????????????? ?????? ????????????????!")));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        Role roleDoctor = initRole("DOCTOR");
        Doctor doctor = initDoctor(roleDoctor, department, null);

        //?????????? ???????????? ????????????????
        mockMvc.perform(post("/api/registrar/doctors/{id}", department.getId())
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data[0].role", Is.is("DOCTOR")))
                .andExpect(jsonPath("$.data[0].lastName", Is.is("l_name")))
                .andExpect(jsonPath("$.data[0].firstName", Is.is("f_name")))
                .andExpect(jsonPath("$.data[0].department", Is.is("Therapy")))
                .andExpect(jsonPath("$.data[0].birthday", Matchers.notNullValue()));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        //?????????????? ?? ?????????? id ??????
        mockMvc.perform(post("/api/registrar/talons/{id}", 1000)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(414)))
                .andExpect(jsonPath("$.text", Is.is(
                        "?????????????? ?? ?????????? id ??????!")));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        //?? ?????????????? ?? ???????? id ?????? ??????????????
        mockMvc.perform(post("/api/registrar/talons/{id}", doctor.getId())
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(415)))
                .andExpect(jsonPath("$.text", Is.is(
                        "?? ?????????????? ?????? ??????????????!")));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));

        Role rolePatient = initRole("PATIENT");
        Patient patient = initPatient(rolePatient);
        talonService.persistTalonsForDoctorAndPatient(doctor, patient,14, 4);

        //?????????? ?????????????? ??????????????
        mockMvc.perform(post("/api/registrar/talons/{id}", doctor.getId())
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data[0].doctorId", Is.is(doctor.getId().intValue())));
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));
    }

}
