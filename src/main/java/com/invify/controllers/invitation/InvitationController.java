package com.invify.controllers.invitation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.invify.dto.APIResponseDTO;
import com.invify.dto.APIResponsePageDTO;
import com.invify.dto.invitation.InvitationRequestDTO;
import com.invify.services.invitation.InvitationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/invitations")
public class InvitationController {

    private final InvitationService invitationService;


    @PostMapping("/get-all-invitations")
    public APIResponsePageDTO getAllInvitations(@RequestBody InvitationRequestDTO request) {
        return invitationService.getInvitations(request);
    }

    @GetMapping("/{invitationId}")
    public APIResponseDTO getInvitation(@PathVariable UUID invitationId) {
        return invitationService.getInvitationDetails(invitationId);
    }

    @PostMapping("/create")
    public APIResponseDTO create(@Valid @ModelAttribute InvitationRequestDTO request) throws JsonProcessingException {
        return invitationService.create(request);
    }

    @PutMapping("/edit/{invitationId}")
    public APIResponseDTO edit(@Valid @ModelAttribute InvitationRequestDTO request, @PathVariable UUID invitationId) throws JsonProcessingException {
        return invitationService.edit(request, invitationId);
    }
}
