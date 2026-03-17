package com.invify.repositories;


import com.invify.entities.InvitationTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface InvitationTransactionRepository extends JpaRepository<InvitationTransaction, UUID> {


    @Query(value = "SELECT nextval('invitation_trx_no_seq')", nativeQuery = true)
    Long getOrderNoSequence();

    InvitationTransaction findByInvitation_InvitationId(UUID invitationId);
}
