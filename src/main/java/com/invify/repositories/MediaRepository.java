package com.invify.repositories;

import com.invify.entities.Invitation;
import com.invify.entities.InvitationMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MediaRepository extends JpaRepository<InvitationMedia, UUID> {
    List<InvitationMedia> findAllByInvitation(Invitation invitation);

    @Modifying
    @Query("DELETE FROM InvitationMedia m WHERE m.invitation.invitationId = :invitationId")
    void deleteAllByInvitation_InvitationId(UUID invitationId);
}
