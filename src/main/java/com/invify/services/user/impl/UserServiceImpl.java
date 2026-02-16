package com.invify.services.user.impl;

import com.invify.dto.APIResponseDTO;
import com.invify.dto.APIResponsePageDTO;
import com.invify.dto.UserRequest;
import com.invify.dto.UserResponseDTO;
import com.invify.entities.User;
import com.invify.enums.ReturnCode;
import com.invify.enums.Role;
import com.invify.repositories.UserRepository;
import com.invify.services.user.UserService;
import com.invify.utils.ResponseAPIUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public APIResponsePageDTO getAllUsersCustomer(UserRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize(), Sort.by("createdDate").descending());

        Page<User> allUsers = userRepository.searchByRoleAndContact(Role.CUSTOMER, request.getEmail(), request.getFullName(), pageRequest);

        Page<UserResponseDTO> responseDTOPage = allUsers.map(user -> UserResponseDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .subscriptionPlan(user.getSubscriptionPlan())
                .activeStatus(user.getActiveStatus())
                .createdDate(user.getCreatedDate().toLocalDate())
                .orderCount(10)
                .build());


        return ResponseAPIUtil.success(
                responseDTOPage.getContent(),
                ReturnCode.DATA_SUCCESSFULLY_FETCHED.getMessage(),
                allUsers.getNumber(),
                allUsers.getTotalElements(),
                allUsers.getTotalPages()
        );
    }


    @Transactional
    @Override
    public APIResponseDTO deleteUser(UUID userId) {
        try {
            userRepository.updateActiveStatus(userId, 0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return ResponseAPIUtil.success(ReturnCode.DATA_SUCCESSFULLY_DELETED.getCode(), ReturnCode.DATA_SUCCESSFULLY_DELETED.getMessage());
    }
}
