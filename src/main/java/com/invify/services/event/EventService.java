package com.invify.services.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invify.dto.invitation.InvitationRequestDTO;
import com.invify.entities.Invitation;
import com.invify.entities.InvitationEvent;
import com.invify.repositories.EventRepository;
import com.invify.repositories.InvitationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;
    private final InvitationRepository invitationRepository;

    public List<InvitationEvent> getEventListByInvitation(Invitation invitation){
        return eventRepository.findAllByInvitation(invitation);
    }

    public List<InvitationEvent> createEvent(InvitationRequestDTO request, Invitation invitation) throws JsonProcessingException {
        List<InvitationRequestDTO.Event> events = objectMapper.readValue(request.getEventJson(), new TypeReference<List<InvitationRequestDTO.Event>>() {});

        List<InvitationEvent> eventList = new ArrayList<>();

        for (int i = 0; i < events.size(); i++) {
            InvitationRequestDTO.Event event = events.get(i);
            InvitationEvent invitationEvent = new InvitationEvent();

            invitationEvent.setEventId(UUID.randomUUID());
            invitationEvent.setEventName(event.getEventName());
            invitationEvent.setEventDate(event.getEventDate());
            invitationEvent.setEventEndDate(event.getEventEndDate());
            invitationEvent.setEventLocation(event.getEventLocation());
            invitationEvent.setEventAddress(event.getEventAddress());
            invitationEvent.setEventMapUrl(event.getEventMapUrl());
            invitationEvent.setInvitation(invitation);
            invitationEvent.setEventOrder(i + 1);

            eventList.add(invitationEvent);

        }

        return eventList;
    }

    public void saveEvent(List<InvitationEvent> events) {
        eventRepository.saveAll(events);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteEventByInvitationId(UUID invitationId) {
        eventRepository.deleteAllByInvitation_InvitationId(invitationId);
    }
}
