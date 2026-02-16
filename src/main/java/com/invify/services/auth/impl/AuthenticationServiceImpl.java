package com.invify.services.auth.impl;

import com.invify.dto.APIResponseDTO;
import com.invify.dto.LoginResponseDTO;
import com.invify.dto.UserLoginDTO;
import com.invify.dto.UserRegisterDTO;
import com.invify.entities.User;
import com.invify.enums.ReturnCode;
import com.invify.enums.Role;
import com.invify.repositories.UserRepository;
import com.invify.services.JwtService;
import com.invify.services.auth.AuthenticationService;
import com.invify.utils.ResponseAPIUtil;
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
    public APIResponseDTO createAdmin(UserRegisterDTO request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .role(Role.ADMIN)
                .createdDate(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return ResponseAPIUtil.success(ReturnCode.DATA_SUCCESSFULLY_CREATED.getCode(), ReturnCode.DATA_SUCCESSFULLY_CREATED.getMessage());
    }

    @Override
    public APIResponseDTO registerUser(UserRegisterDTO userRegisterDTO) {
        userRepository.findByEmail(userRegisterDTO.getEmail()).ifPresent(user -> {
            throw new RuntimeException("Email already exists");
        });

        User user = User.builder()
                .userId(UUID.randomUUID())
                .email(userRegisterDTO.getEmail())
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .fullName(userRegisterDTO.getFullName())
                .role(Role.CUSTOMER)
                .activeStatus(1)
                .createdDate(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return ResponseAPIUtil.success(ReturnCode.DATA_SUCCESSFULLY_CREATED.getCode(), ReturnCode.DATA_SUCCESSFULLY_CREATED.getMessage());
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
            throw new BadCredentialsException("Invalid email or password");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        User user = userRepository.findByEmail(userLoginDTO.getEmail()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        String token = jwtService.generateToken(user);
        LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
                .token(token)
                .expiresIn(jwtService.getJwtExpirations())
                .role(user.getRole().name())
                .build();

        return ResponseAPIUtil.success(loginResponseDTO, ReturnCode.LOGIN_SUCCESSFULLY.getMessage());
    }
}
