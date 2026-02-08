package com.invify.controllers.auth;

import com.invify.dto.APIResponseDTO;
import com.invify.dto.UserRegisterDTO;
import com.invify.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/auth")
public class AuthAdminController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public APIResponseDTO registerAdmin(@RequestBody UserRegisterDTO request) {
        return authenticationService.createAdmin(request);
    }

}
