package com.invify.services.auth.impl;

import com.invify.entities.User;
import com.invify.enums.Role;
import com.invify.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("admin@invify.com").isEmpty()){
            User user = User.builder()
                    .userId(UUID.randomUUID())
                    .email("admin@invify.com")
                    .fullName("Super Admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .createdDate(LocalDateTime.now())
                    .build();
            userRepository.save(user);
        }
    }
}
