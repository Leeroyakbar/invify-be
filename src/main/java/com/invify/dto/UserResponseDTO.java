package com.invify.dto;

import com.invify.enums.SubscriptionPlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private UUID userId;
    private String email;
    private String fullName;
    private String role;
    private LocalDate createdDate;
    private Integer orderCount;
    private SubscriptionPlan subscriptionPlan;
    private Integer activeStatus;

}
