package com.invify.dto.invitation;

import com.invify.dto.event.EventResponseDTO;
import com.invify.dto.media.MediaResponseDTO;
import com.invify.dto.template.TemplateResponseDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvitationDetailResponseDTO {

    private UUID invitationId;
    private UUID userId;
    private String fullName;
    private String slug;
    private String subscriptionPlan;
    private BigDecimal price;
    private String brideName;
    private String groomName;
    private TemplateResponseDTO template;
    private List<EventResponseDTO> events;
    private List<MediaResponseDTO> gallery;
    private Integer activeStatus;
    private LocalDate expiredDate;
}
