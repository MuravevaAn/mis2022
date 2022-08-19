package ru.mis2022.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import ru.mis2022.models.entity.Administrator;
import ru.mis2022.models.entity.Attestation;
import ru.mis2022.models.entity.Department;
import ru.mis2022.models.entity.Diploma;
import ru.mis2022.models.entity.Disease;
import ru.mis2022.models.entity.Doctor;
import ru.mis2022.models.entity.Economist;
import ru.mis2022.models.entity.HrManager;
import ru.mis2022.models.entity.MedicalOrganization;
import ru.mis2022.models.entity.MedicalService;
import ru.mis2022.models.entity.Patient;
import ru.mis2022.models.entity.PersonalHistory;
import ru.mis2022.models.entity.PriceOfMedicalService;
import ru.mis2022.models.entity.Registrar;
import ru.mis2022.models.entity.Role;
import ru.mis2022.models.entity.Talon;
import ru.mis2022.service.entity.DepartmentService;
import ru.mis2022.service.entity.DiseaseService;
import ru.mis2022.service.entity.MedicalServiceService;
import ru.mis2022.service.entity.PatientService;
import ru.mis2022.service.entity.DoctorService;
import ru.mis2022.service.entity.EconomistService;
import ru.mis2022.service.entity.PriceOfMedicalServiceService;
import ru.mis2022.service.entity.RoleService;
import ru.mis2022.service.entity.MedicalOrganizationService;
import ru.mis2022.service.entity.RegistrarService;
import ru.mis2022.service.entity.AdministratorService;
import ru.mis2022.service.entity.TalonService;
import ru.mis2022.service.entity.HrManagerService;
import ru.mis2022.service.entity.AttestationService;
import ru.mis2022.service.entity.PersonalHistoryService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ru.mis2022.models.entity.Role.RolesEnum;


@Component
@ConditionalOnExpression("${mis.property.runInitialize:true}")
public class DataInitializer {

    @Value("${mis.property.doctorSchedule}")
    private Integer numberOfDaysDoctor;
    @Value("${mis.property.talon}")
    private Integer numbersOfTalons;
    @Value("${mis.property.patientSchedule}")
    private Integer numberOfDaysPatient;

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final EconomistService economistService;
    private final RoleService roleService;
    private final MedicalOrganizationService medicalOrganizationService;
    private final DepartmentService departmentService;
    private final RegistrarService registrarService;
    private final AdministratorService administratorService;
    private final HrManagerService hrManagerService;
    // todo удалить неиспользуемое
    private final AttestationService attestationService;
    private final PersonalHistoryService personalHistoryService;
    private final TalonService talonService;
    private final MedicalServiceService medicalServiceService;
    private final DiseaseService diseaseService;
    private final PriceOfMedicalServiceService priceOfMedicalServiceService;


    public DataInitializer(PatientService patientService,
                           DoctorService doctorService,
                           EconomistService economistService,
                           RoleService roleService,
                           MedicalOrganizationService medicalOrganizationService,
                           DepartmentService departmentService,
                           RegistrarService registrarService,
                           AdministratorService administratorService,
                           TalonService talonService,
                           HrManagerService hrManagerService,
                           AttestationService attestationService,
                           PersonalHistoryService personalHistoryService,
                           MedicalServiceService medicalServiceService,
                           DiseaseService diseaseService,
                           PriceOfMedicalServiceService priceOfMedicalServiceService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.economistService = economistService;
        this.roleService = roleService;
        this.medicalOrganizationService = medicalOrganizationService;
        this.departmentService = departmentService;
        this.registrarService = registrarService;
        this.administratorService = administratorService;
        this.hrManagerService = hrManagerService;
        this.talonService = talonService;
        this.attestationService = attestationService;
        this.personalHistoryService = personalHistoryService;
        this.medicalServiceService = medicalServiceService;
        this.diseaseService = diseaseService;
        this.priceOfMedicalServiceService = priceOfMedicalServiceService;
    }

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int FIVE = 5;
    private static final int SOME = 10;
    private static final int ANY = 20;
    private static final int MANY = 50;
    private static final int VERY_MANY = 100;

    private static final int MEDICAL_ORGANIZATION = ONE;
    private static final int DEPARTMENT_COUNT = intInRange(TWO, FIVE);
    private static final int DOCTOR_COUNT = intInRange(FIVE, SOME);
    private static final int DIPLOMA_COUNT = intInRange(ONE, FIVE);
    private static final int MAIN_DOCTORS_COUNT = intInRange(ONE, TWO);
    private static final int DISEASE_COUNT = intInRange(FIVE, ANY);
    private static final int MEDICAL_SERVICE_COUNT = intInRange(MANY, VERY_MANY);
    private static final int ADMIN = ONE;
    private static final int HR_MANAGER = ONE;
    private static final int ECONOMIST = ONE;
    private static final int REGISTRAR_COUNT = intInRange(TWO, FIVE);
    private static final int PATIENT_COUNT = intInRange(ANY, MANY);

    private static int intInRange(int startBorder, int endBorder) {
        return new Random().nextInt(++endBorder - startBorder) + startBorder;
    }

    private static LocalDate randomBirthday() {
        LocalDate beginDate = LocalDate.now().minusYears(65);
        LocalDate endDate = LocalDate.now().minusYears(25);
        return dateInRange(beginDate, endDate);
    }

    private static LocalDate dateInRange(LocalDate startBorder, LocalDate endBorder) {
        long randomDay = new Random().nextLong(endBorder.toEpochDay() - startBorder.toEpochDay()) +
                startBorder.toEpochDay();
        return LocalDate.ofEpochDay(randomDay);
    }

    @PostConstruct
    public void addTestData() {
        Role roleRegistrar = roleService.persist(new Role(RolesEnum.REGISTRAR.name()));
        Role roleDoctor = roleService.persist(new Role(RolesEnum.DOCTOR.name()));
        Role rolePatient = roleService.persist(new Role(RolesEnum.PATIENT.name()));
        Role roleMainDoctor = roleService.persist(new Role(RolesEnum.MAIN_DOCTOR.name()));
        Role roleEconomist = roleService.persist(new Role(RolesEnum.ECONOMIST.name()));
        Role roleAdmin = roleService.persist((new Role(RolesEnum.ADMIN.name())));
        Role roleHrManager = roleService.persist(new Role(RolesEnum.HR_MANAGER.name()));
        Role roleChiefDoctor=roleService.persist(new Role(RolesEnum.CHIEF_DOCTOR.name()));

        //Создать одну медицинскую организацию
        MedicalOrganization medicalOrganization = new MedicalOrganization(
                "medicalOrganization",
                "address"
        );

        //Создать отделения для медицинской организации
        List<Department> departmentList = new ArrayList<>();
        for (int i = 1; i <= DEPARTMENT_COUNT; i++ ) {
            Department department = new Department("DepartmentName" + i);
            departmentService.persist(department);

            //Создать заболевания для отделений
            List<Disease> departmentDisease = new ArrayList<>();
            for (int k = 1; k <= DISEASE_COUNT / DEPARTMENT_COUNT; k++) {
                Disease disease = new Disease(
                        "AO" + i + k,
                        "diseaseName" + i + k
                );
                disease.setDepartment(department);
                departmentDisease.add(disease);
            }

            //Создать медицинские услуги для отделений
            List<MedicalService> medicalServicesDepartment = new ArrayList<>();
            for (int j = 1; j <= MEDICAL_SERVICE_COUNT / DEPARTMENT_COUNT; j++) {
                MedicalService service = new MedicalService(
                        "AP125" + i + j,
                        "serviceName" + i + j
                );
                medicalServiceService.persist(service);

            }
        }

        // Админстратор
        for (int i = 1; i <= ADMIN; i++) {
            Administrator administrator = new Administrator(
                    "administrator" + i + "@email.com",
                    "administrator" + i,
                    "administratorFirstName" + i,
                    "administratorLastName" + i,
                    "administratorSurname" + i,
                    randomBirthday(),
                    roleRegistrar
            );
            administratorService.persist(administrator);
        }

        //HR Manager
        for (int i = 1; i <= HR_MANAGER; i++) {
            HrManager hrManager = new HrManager(
                    "hrManager" + i + "@email.com",
                    "hrManager" + i,
                    "hrManagerFirstName" + i,
                    "hrManagerLastName" + i,
                    "hrManagerSurname" + i,
                    randomBirthday(),
                    roleHrManager
            );
            hrManagerService.persist(hrManager);
        }

        //Экономист
        for (int i = 1; i <= ECONOMIST; i++) {
            Economist economist = new Economist(
                    "economist" + i + "@email.com",
                    "economist" + i,
                    "economistFirstName" + i,
                    "economistLastName" + i,
                    "economistSurname" + i,
                    randomBirthday(),
                    roleEconomist
            );
            economistService.persist(economist);
        }

        //Регистратор
        for (int i = 1; i <= REGISTRAR_COUNT; i++) {
            Registrar registrar = new Registrar(
                    "registrar" + i + "@email.com",
                    "registrar" + i,
                    "registrarFirstName" + i,
                    "registrarLastName" + i,
                    "registrarSurname" + i,
                    randomBirthday(),
                    roleRegistrar
            );
            registrarService.persist(registrar);
        }

        //Пациенты
        List<Patient> patients = new ArrayList<>();
        for (int i = 1; i <= PATIENT_COUNT; i++) {
            Patient patient = new Patient(
                    "patient" + i + "@email.com",
                    "patient" + i,
                    "patientFirstName" + i,
                    "patientLastName" + i,
                    "patientSurname" + i,
                    randomBirthday(),
                    rolePatient,
                    "passport" + i,
                    "polis" + i,
                    "snils" + i,
                    "address" + i
            );
            patientService.persist(patient);
            patients.add(patient);
        }

        //Доктора
        List<Doctor> doctors = new ArrayList<>();
        for (Department department : departmentList) {
            long departmentId = department.getId();
            for (int i = 1; i <= DOCTOR_COUNT; i++) {
                Role role = (i <= MAIN_DOCTORS_COUNT) ? roleChiefDoctor : roleDoctor;
                Doctor doctor = new Doctor(
                        "doctor" + departmentId + i + "@email.com",
                        "doctor" + departmentId + i,
                        "doctorFirstName" + departmentId + i,
                        "doctorLastName" + departmentId + i,
                        "doctorSurname" + departmentId + i,
                        randomBirthday(),
                        role,
                        department
                );
                doctorService.persist(doctor);
                doctors.add(doctor);

            }



        }



//
//
//
//
//
//
//
//
//
//        for (int num = 1; num < 10; num++) {
//            patientService.persist(new Patient(
//                    "patient" + num + "@email.com",
//                    String.valueOf(num),
//                    "f_name_" + num,
//                    "l_name_" + num,
//                    "surname_" + num,
//                    LocalDate.now().minusYears(20),
//                    rolePatient,
//                    "passport_" + num,
//                    "polis_" + num,
//                    "snils_" + num,
//                    "address_" + num));
//        }
//
////        MedicalOrganization medicalOrganization = new MedicalOrganization();
////        medicalOrganization.setName("City Hospital");
////        medicalOrganization.setAddress("Moscow, Pravda street, 30");
////        medicalOrganizationService.persist(medicalOrganization);
////
////        MedicalOrganization medicalOrganization1 = new MedicalOrganization();
////        medicalOrganization1.setName("Hospital №1");
////        medicalOrganization1.setAddress("St. Peterburg, Lenina avenue, 3");
////        medicalOrganizationService.persist(medicalOrganization1);
////
////        MedicalOrganization medicalOrganization2 = new MedicalOrganization();
////        medicalOrganization2.setName("City Clinic Hospital");
////        medicalOrganization2.setAddress("Saratov, Grin street, 25");
////        medicalOrganizationService.persist(medicalOrganization2);
//
//        departmentService.persist(new Department("Therapy", medicalOrganization));
//        departmentService.persist(new Department("Surgery", medicalOrganization));
//        departmentService.persist(new Department("Cardiology", medicalOrganization));
//        departmentService.persist(new Department("Dentistry", medicalOrganization));
//        departmentService.persist(new Department("Dermatology", medicalOrganization));
//        departmentService.persist(new Department("Pediatrics", medicalOrganization));
//        departmentService.persist(new Department("Psychiatry", medicalOrganization));
//
//        Department dep31 = new Department();
//        dep31.setName("Therapy");
//        dep31.setMedicalOrganization(medicalOrganization2);
//        departmentService.persist(dep31);
//
//        Department dep32 = new Department();
//        dep32.setName("Dermatology");
//        dep32.setMedicalOrganization(medicalOrganization2);
//        departmentService.persist(dep32);
//
//        Department dep33 = new Department();
//        dep33.setName("Pediatrics");
//        dep33.setMedicalOrganization(medicalOrganization2);
//        departmentService.persist(dep33);
//
//
//        for (int num = 1; num < 10; num++) {
//            economistService.persist(new Economist(
//                    "economist" + num + "@email.com",
//                    String.valueOf(num),
//                    "f_name_" + num,
//                    "l_name_" + num,
//                    "surname_" + num,
//                    LocalDate.now().minusYears(20),
//                    roleEconomist
//            ));
//        }
//
//        for (int num = 1; num < 2; num++) {
//            doctorService.persist(new Doctor(
//                    "mainDoctor" + num + "@email.com",
//                    String.valueOf(num),
//                    "f_name_" + num,
//                    "l_name_" + num,
//                    "surname_" + num,
//                    LocalDate.now().minusYears(20),
//                    roleMainDoctor,
//                    departmentService.persist(new Department("Therapy", medicalOrganization))
//            ));
//        }
//
//        for (int num = 1; num < 8; num++) {
//            doctorService.persist(new Doctor(
//                    "doctor" + num + "@email.com",
//                    String.valueOf(num),
//                    "f_name_" + num,
//                    "l_name_" + num,
//                    "surname_" + num,
//                    LocalDate.now().minusYears(20),
//                    roleDoctor,
//                    dep33
//            ));
//        }
//
//        Doctor doctor = doctorService.findByEmail("doctor1@email.com");
//        Doctor doctor2 = doctorService.findByEmail("doctor2@email.com");
//        Doctor doctor3 = doctorService.findByEmail("doctor3@email.com");
//        Doctor doctor4 = doctorService.findByEmail("doctor4@email.com");
//        Patient patient = patientService.findByEmail("patient1@email.com");
//        talonService.persistTalonsForDoctorAndPatient(doctor, patient, numberOfDays, numbersOfTalons);
//        talonService.persistTalonsForDoctorAndPatient(doctor2, null, numberOfDays, numbersOfTalons);
//        talonService.persistTalonsForDoctorAndPatient(doctor3, null, numberOfDays, numbersOfTalons);
//        talonService.persistTalonsForDoctorAndPatient(doctor4, null, numberOfDays, numbersOfTalons);
//
//
//        for (int num = 1; num < 2; num++) {
//            registrarService.persist(new Registrar(
//                    "registrar" + num + "@email.com",
//                    String.valueOf(num),
//                    "f_name_" + num,
//                    "l_name_" + num,
//                    "surname_" + num,
//                    LocalDate.now().minusYears(20),
//                    roleRegistrar
//            ));
//        }
//        for (int num = 1; num < 2; num++) {
//            administratorService.persist(new Administrator(
//                    "administrator" + num + "@email.com",
//                    String.valueOf(num),
//                    "f_name_" + num,
//                    "l_name_" + num,
//                    "surname_" + num,
//                    LocalDate.now().minusYears(20),
//                    roleAdmin
//            ));
//        }
//
//        for (int num = 1; num < 2; num++) {
//            hrManagerService.persist(new HrManager(
//                    "hrManager" + num + "@email.com",
//                    String.valueOf(num),
//                    "f_name_" + num,
//                    "l_name_" + num,
//                    "surname_" + num,
//                    LocalDate.now().minusYears(20),
//                    roleHrManager
//            ));
//        }
    }
 }