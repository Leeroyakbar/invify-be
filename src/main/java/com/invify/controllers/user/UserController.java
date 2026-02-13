package com.invify.controllers.user;

import com.invify.dto.APIResponsePageDTO;
import com.invify.dto.UserRequest;
import com.invify.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/get-all-customers")
    public APIResponsePageDTO getAllUserCustomer(@RequestBody UserRequest request) {
        return userService.getAllUsersCustomer(request);
    }
}
