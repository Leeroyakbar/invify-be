package com.invify.services.auth;

import com.invify.dto.APIResponseDTO;
import com.invify.dto.UserLoginDTO;
import com.invify.dto.UserRegisterDTO;

public interface AuthenticationService {
    APIResponseDTO registerUser(UserRegisterDTO userRegisterDTO);
    APIResponseDTO loginUser(UserLoginDTO userLoginDTO);
}
