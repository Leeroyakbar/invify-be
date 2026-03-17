package com.invify.dto.invitation;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvitationResponseDTO {
    private UUID invitationId;
    private String coupleName;
    private String templateName;
    private String templateCategory;
    private String slug;
    private Integer activeStatus;
    private LocalDate expiredDate;
}
