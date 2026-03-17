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
@Table(name = "MST_INVITATION_RSVP")
public class InvitationRSVP {

    @Id
    @Column(name = "RSVP_ID")
    private UUID rsvpId;

    @Column(name = "RSVP_NAME")
    private String rsvpName;

    @Column(name = "RSVP_ATTENDANCE")
    private Integer rsvpAttendance;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVITATION_ID", nullable = false)
    private Invitation invitation;
}
