package com.invify.entities;


import com.invify.enums.SubscriptionPlan;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "TRX_INVITATION")
public class InvitationTransaction {

    @Id
    private UUID trxId;

    @Column(name = "TRX_NO")
    private String trxNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVITATION_ID")
    private Invitation invitation;

    @Enumerated(EnumType.STRING)
    @Column(name = "SUBSCRIPTION_PLAN")
    private SubscriptionPlan subscriptionPlan;

    @Column(name = "TRX_AMOUNT")
    private BigDecimal trxAmount;

    @Column(name = "ACTIVE_STATUS")
    private Integer activeStatus;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;
}
