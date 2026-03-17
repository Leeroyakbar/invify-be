package com.invify.services.invitation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.invify.dto.APIResponseDTO;
import com.invify.dto.APIResponsePageDTO;
import com.invify.dto.invitation.InvitationRequestDTO;
import jakarta.validation.Valid;

import java.util.UUID;

public interface InvitationService {

    APIResponsePageDTO getInvitations(InvitationRequestDTO request);

    APIResponseDTO getInvitationDetails(UUID invitationId);

    APIResponseDTO create(InvitationRequestDTO request) throws JsonProcessingException;

    APIResponseDTO edit(InvitationRequestDTO request, UUID invitationId) throws JsonProcessingException;
}
