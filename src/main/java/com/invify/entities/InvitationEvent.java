package com.invify.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MST_INVITATION_EVENTS")
public class InvitationEvent {

    @Id
    @Column(name = "EVENT_ID")
    private UUID eventId;

    @Column(name = "EVENT_NAME")
    private String eventName;

    @Column(name = "EVENT_DATE")
    private LocalDateTime eventDate;

    @Column(name = "EVENT_END_DATE")
    private LocalDateTime eventEndDate;

    @Column(name = "EVENT_LOCATION")
    private String eventLocation;

    @Column(name = "EVENT_ADDRESS")
    private String eventAddress;

    @Column(name = "EVENT_MAP_URL")
    private String eventMapUrl;

    @Column(name = "EVENT_ORDER")
    private Integer eventOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVITATION_ID", nullable = false)
    private Invitation invitation;
}
