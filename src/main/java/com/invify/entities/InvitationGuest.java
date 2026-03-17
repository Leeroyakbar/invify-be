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
@Table(name = "MST_INVITATION_GUESTS")
public class InvitationGuest {

    @Id
    @Column(name = "GUEST_ID")
    private UUID guestId;

    @Column(name = "GUEST_NAME")
    private String guestName;

    @Column(name = "GUEST_GROUP")
    private String guestGroup;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "SENT_STATUS")
    private Integer sentStatus;

    @Column(name = "SENT_DATE")
    private LocalDateTime sentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVITATION_ID", nullable = false)
    private Invitation invitation;
}
