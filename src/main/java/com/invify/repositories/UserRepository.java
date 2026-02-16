package com.invify.repositories;

import com.invify.entities.User;
import com.invify.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Page<User> findAllByRole(Role role, Pageable pageable);
    Page<User> findAllByRoleOrEmailOrFullName(Role role, String email, String fullName, Pageable pageable);

    @Query(value = "SELECT u FROM User u WHERE u.role = ?1 " +
            "AND (LOWER(u.email) LIKE LOWER(CONCAT(?2, '%'))" +
            "AND LOWER(u.fullName) LIKE LOWER(CONCAT(?3, '%')))" +
            "AND u.activeStatus = 1")
    Page<User> searchByRoleAndContact(Role role, String email, String fullName, Pageable pageable);


    @Modifying
    @Query(value = "UPDATE User u set u.activeStatus = :activeStatus where u.userId = :userId ")
    void updateActiveStatus(UUID userId, Integer activeStatus);
}
