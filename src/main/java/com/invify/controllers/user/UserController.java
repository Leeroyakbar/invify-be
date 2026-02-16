package com.invify.controllers.user;

import com.invify.dto.APIResponseDTO;
import com.invify.dto.APIResponsePageDTO;
import com.invify.dto.UserRequest;
import com.invify.services.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/get-all-customers")
    public APIResponsePageDTO getAllUserCustomer(@RequestBody UserRequest request) {
        return userService.getAllUsersCustomer(request);
    }

    @DeleteMapping("/{userId}")
    public APIResponseDTO deleteUser(@PathVariable UUID userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping("/update-user")
    public APIResponseDTO updateUser(@Valid @RequestBody UserRequest userRequest) {
        return userService.updateUser(userRequest);
    }
}
