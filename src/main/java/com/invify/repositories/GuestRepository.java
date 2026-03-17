package com.invify.repositories;

import com.invify.entities.InvitationGuest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GuestRepository extends JpaRepository<InvitationGuest, UUID> {
}
