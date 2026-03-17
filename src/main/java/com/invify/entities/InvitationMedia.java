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
@Table(name = "MST_INVITATION_MEDIAS")
public class InvitationMedia {

    @Id
    @Column(name = "MEDIA_ID")
    private UUID mediaId;

    @Column(name = "MEDIA_TYPE")
    private String mediaType;

    @Column(name = "MEDIA_URL")
    private String mediaUrl;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    @Column(name = "ACTIVE_STATUS")
    private Integer activeStatus;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVITATION_ID", nullable = false)
    private Invitation invitation;
}
