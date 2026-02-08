package com.invify.controllers.auth;

import com.invify.dto.APIResponseDTO;
import com.invify.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public APIResponseDTO getAllUser() {
        return userService.getAllUsers();
    }
}
