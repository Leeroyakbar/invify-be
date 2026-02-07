package com.invify.controllers.auth;

import com.invify.dto.APIResponseDTO;
import com.invify.dto.UserLoginDTO;
import com.invify.dto.UserRegisterDTO;
import com.invify.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @PostMapping("/register")
    public APIResponseDTO registerUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        return authenticationService.registerUser(userRegisterDTO);
    }

    @PostMapping("/login")
    public APIResponseDTO loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        return authenticationService.loginUser(userLoginDTO);
    }
}

