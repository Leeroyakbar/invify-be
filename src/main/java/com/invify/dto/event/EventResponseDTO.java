package com.invify.dto.event;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventResponseDTO {
    private UUID eventId;
    private String eventName;
    private LocalDateTime eventDate;
    private LocalDateTime eventEndDate;
    private String eventLocation;
    private String eventAddress;
    private String eventMapUrl;
    private Integer eventOrder;
}
