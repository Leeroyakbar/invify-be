package com.invify.services.user.impl;

import com.invify.dto.APIResponseDTO;
import com.invify.dto.UserResponseDTO;
import com.invify.entities.User;
import com.invify.enums.ReturnCode;
import com.invify.repositories.UserRepository;
import com.invify.services.user.UserService;
import com.invify.utils.ResponseAPIUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public APIResponseDTO getAllUsers() {
        List<User> allUsers = userRepository.findAll();

        List<UserResponseDTO> responseDTOList = new ArrayList<>();
        allUsers.forEach(user -> {
            UserResponseDTO res = new UserResponseDTO();

            res.setUserId(user.getUserId());
            res.setEmail(user.getEmail());
            res.setFullName(user.getFullName());
            res.setRole(user.getRole().name());

            responseDTOList.add(res);
        });


        return ResponseAPIUtil.success(responseDTOList, ReturnCode.DATA_SUCCESSFULLY_FETCHED.getMessage());
    }
}
