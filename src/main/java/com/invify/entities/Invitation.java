package com.invify.entities;

import com.invify.enums.SubscriptionPlan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MST_INVITATIONS")
public class Invitation {

    @Id
    @Column(name = "INVITATION_ID")
    private UUID invitationId;

    @Column(name = "SLUG", unique = true, nullable = false)
    private String slug;

    @Column(name = "BRIDE_NAME", nullable = false)
    private String brideName;

    @Column(name = "GROOM_NAME", nullable = false)
    private String groomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEMPLATE_ID", nullable = false)
    private Template template;

    @Column(name = "ACTIVE_STATUS")
    private Integer activeStatus;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    @Column(name = "EXPIRED_DATE")
    private LocalDate expiredDate;


}
