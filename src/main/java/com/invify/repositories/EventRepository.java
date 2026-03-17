package com.invify.repositories;

import com.invify.entities.Invitation;
import com.invify.entities.InvitationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<InvitationEvent, UUID> {
    List<InvitationEvent> findAllByInvitation(Invitation invitation);

    @Modifying
    @Query("DELETE FROM InvitationEvent ie WHERE ie.invitation.invitationId = ?1 ")
    void deleteAllByInvitation_InvitationId(UUID invitationId);
}
