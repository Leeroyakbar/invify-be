package com.invify.services.auth.impl;

import com.invify.dto.APIResponseDTO;
import com.invify.dto.LoginResponseDTO;
import com.invify.dto.UserLoginDTO;
import com.invify.dto.UserRegisterDTO;
import com.invify.entities.User;
import com.invify.enums.ReturnCode;
import com.invify.repositories.UserRepository;
import com.invify.services.JwtService;
import com.invify.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public APIResponseDTO registerUser(UserRegisterDTO userRegisterDTO) {
        User user = User.builder()
                .userId(UUID.randomUUID())
                .email(userRegisterDTO.getEmail())
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .fullName(userRegisterDTO.getFullName())
                .createdDate(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return APIResponseDTO.builder()
                .success(true)
                .message(ReturnCode.DATA_SUCCESSFULLY_CREATED.getMessage())
                .data(ReturnCode.DATA_SUCCESSFULLY_CREATED.getCode())
                .build();
    }

    @Override
    public APIResponseDTO loginUser(UserLoginDTO userLoginDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDTO.getEmail(),
                            userLoginDTO.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        User user = userRepository.findByEmail(userLoginDTO.getEmail()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        String token = jwtService.generateToken(user);
        LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
                .token(token)
                .expiresIn(jwtService.getJwtExpirations())
                .build();

        return APIResponseDTO.builder()
                .message(ReturnCode.LOGIN_SUCCESSFULLY.getMessage())
                .success(true)
                .data(loginResponseDTO)
                .build();

    }
}
