package com.invify.dto.invitation;

import com.invify.dto.PageableRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvitationRequestDTO extends PageableRequest {
    private UUID userId;

    @NotNull(message = "couple name is required")
    @NotBlank(message = "couple name is required")
    private String coupleName;

    @NotNull(message = "template id is required")
    private UUID templateId;
    private String templateName;

    @NotNull(message = "subscription plan is required")
    private String subscriptionPlan;
    private LocalDate expiredDate;

    // bride and groom photos
    @NotNull(message = "bride photo is required")
    private MultipartFile bridePhoto;

    @NotNull(message = "groom photo is required")
    private MultipartFile groomPhoto;

    private BigDecimal trxAmount;


    // gallery
    private List<MultipartFile> gallery;
    private MultipartFile videoBackground;
    @NotNull(message = "music background is required")
    @NotBlank(message = "music background cannot be empty")
    private String musicBackground;

    // event
    @NotNull(message = "event is required")
    @NotBlank(message = "event cannot be empty")
    private String eventJson;


    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Event {
        private String eventName;
        private LocalDateTime eventDate;
        private LocalDateTime eventEndDate;
        private String eventLocation;
        private String eventAddress;
        private String eventMapUrl;
    }
}
