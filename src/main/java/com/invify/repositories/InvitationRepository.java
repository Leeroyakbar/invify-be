package com.invify.repositories;

import com.invify.entities.Invitation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface InvitationRepository extends JpaRepository<Invitation, UUID> {

    @Query("SELECT i FROM Invitation i JOIN FETCH i.template t " +
            "WHERE (:groomName IS NULL OR :groomName = '' OR LOWER(i.groomName) LIKE :groomName " +
            "       OR (:brideName IS NULL OR :brideName = '' OR LOWER(i.brideName) LIKE :brideName)) " +
            "OR (:templateName IS NULL OR :templateName = '' OR LOWER(t.templateName) LIKE :templateName)")
    Page<Invitation> findInvitationsWithFilter(
            @Param("groomName") String groomName,
            @Param("brideName") String brideName,
            @Param("templateName") String templateName,
            Pageable pageable);
}
