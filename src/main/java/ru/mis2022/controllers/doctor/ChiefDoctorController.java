package ru.mis2022.controllers.doctor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.doctor.CurrentDoctorDto;
import ru.mis2022.models.entity.User;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.dto.DoctorDtoService;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('CHIEF_DOCTOR')")
@RequestMapping("/api/chief-doctor")
@Api(tags = "Controller chief doctor")
public class ChiefDoctorController {

    private final DoctorDtoService doctorDtoService;

    @ApiOperation("get a current chief doctor")
    @GetMapping("/mainPage/current")
    public Response<CurrentDoctorDto> getCurrentDoctorDto() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.ok(doctorDtoService.getCurrentDoctorDtoByEmail(currentUser.getEmail()));
    }
}
