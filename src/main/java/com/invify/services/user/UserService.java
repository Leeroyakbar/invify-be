package com.invify.services.user;

import com.invify.dto.APIResponseDTO;
import com.invify.dto.APIResponsePageDTO;
import com.invify.dto.UserRequest;
import com.invify.entities.User;

import java.util.UUID;

public interface UserService {

    User getUserById(UUID userId);

    User getUserDetailByInvitationId(UUID invitationId);

    APIResponseDTO getUserDetail(UserRequest request);

    APIResponsePageDTO getAllUsersCustomer(UserRequest request);

    APIResponseDTO deleteUser(UUID userId);

    APIResponseDTO updateUser(UserRequest userRequest);
}
