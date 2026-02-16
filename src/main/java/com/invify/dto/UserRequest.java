package com.invify.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private UUID userId;
    private String email;
    private String fullName;
    private String subscriptionPlan;

    @NotNull(message = "Active Status tidak boleh kosong")
    @Min(value = 0, message = "Status harus 0 atau 1")
    @Max(value = 1, message = "Status harus 0 atau 1")
    private Integer activeStatus;

    private int page;
    private int size;
}