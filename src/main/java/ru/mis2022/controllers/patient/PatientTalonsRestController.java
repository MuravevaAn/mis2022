package ru.mis2022.controllers.patient;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.Talon.TalonDto;
import ru.mis2022.models.entity.Talon;
import ru.mis2022.models.mapper.TalonMapper;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.entity.PatientService;
import ru.mis2022.service.entity.TalonService;
import ru.mis2022.utils.validation.ApiValidationUtils;

import java.util.List;


@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('PATIENT')")
@RequestMapping("/api/patient/talons")
public class PatientTalonsRestController {
    private final TalonService talonService;
    private final TalonMapper talonMapper;
    private final PatientService patientService;


   @GetMapping("/{patientId}")
    public Response<List<TalonDto>> getAllTalonsByPatientId(@PathVariable Long patientId) {
       ApiValidationUtils
               .expectedNotNull(patientService.isExistById(patientId),
                       402, "Пациента с таким id нет!");
       List<Talon> talons = talonService.findAllByPatientId(patientId);
       //todo не надо возвращать null. верни пустую коллекцию
       if (talons.size() == 0) {
           return null;
       }
       return Response.ok(talonMapper.toListDto(talons));
    }

    @PatchMapping ("/{talonId}/{patientId}")
    public Response<Void> cancelRecordTalons(@PathVariable Long talonId, @PathVariable Long patientId) {

        ApiValidationUtils
                .expectedNotNull(talonService.isExistById(talonId),
                        402, "Талона с таким id нет!");

        ApiValidationUtils
                .expectedNotNull(patientService.isExistById(patientId),
                        403, "Пациента с таким id нет!");
        //todo имя метода isExistById выбрано не правитльно. оно должно возвращать булеан. тут можно воспользоватьсе
        //  штатным методом репозитория getById() или написать метод с именем getTalonById()
        //  и переменная t мне не нравится
        Talon t = talonService.isExistById(talonId);
        t.setPatient(null);
        talonService.merge(t);
        return Response.ok();
    }
}
