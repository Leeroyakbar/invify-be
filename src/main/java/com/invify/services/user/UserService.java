package com.invify.services.user;

import com.invify.dto.APIResponseDTO;
import com.invify.dto.APIResponsePageDTO;
import com.invify.dto.UserRequest;

import java.util.UUID;

public interface UserService {

    APIResponsePageDTO getAllUsersCustomer(UserRequest request);

    APIResponseDTO deleteUser(UUID userId);
}
