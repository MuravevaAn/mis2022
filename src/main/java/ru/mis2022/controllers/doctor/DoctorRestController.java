package ru.mis2022.controllers.doctor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.response.Response;

@RestController
@RequestMapping("/api/doctor")
public class DoctorRestController {

    @GetMapping("/test")
    @PreAuthorize("hasRole('DOCTOR')")
    public Response<String> test() {
        return Response.ok("doctor rest controller");
    }

}
