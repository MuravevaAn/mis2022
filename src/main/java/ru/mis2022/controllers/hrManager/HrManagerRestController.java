package ru.mis2022.controllers.hrManager;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.dto.hr.CurrentHrManagerDto;
import ru.mis2022.models.entity.User;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.dto.HrManagerDtoService;

@Validated
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('HR_MANAGER')")
@RequestMapping("/api/hr_manager")
public class HrManagerRestController {

    @Autowired
    private final HrManagerDtoService hrManagerDtoService;

    @ApiOperation("get a current HrManager")
    @GetMapping("/mainPage/current")
    public Response<CurrentHrManagerDto> getCurrentHrManagerDto() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.ok(hrManagerDtoService.getCurrentHrDtoByEmail(currentUser.getEmail()));
    }
}
