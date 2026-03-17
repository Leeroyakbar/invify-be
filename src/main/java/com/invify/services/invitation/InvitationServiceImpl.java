package com.invify.services.invitation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.invify.dto.APIResponseDTO;
import com.invify.dto.APIResponsePageDTO;
import com.invify.dto.event.EventResponseDTO;
import com.invify.dto.invitation.InvitationDetailResponseDTO;
import com.invify.dto.invitation.InvitationRequestDTO;
import com.invify.dto.invitation.InvitationResponseDTO;
import com.invify.dto.media.MediaResponseDTO;
import com.invify.dto.template.TemplateResponseDTO;
import com.invify.entities.*;
import com.invify.enums.ReturnCode;
import com.invify.enums.SubscriptionPlan;
import com.invify.exceptions.BadRequestException;
import com.invify.exceptions.NotFoundException;
import com.invify.repositories.InvitationRepository;
import com.invify.repositories.TemplateRepository;
import com.invify.services.event.EventService;
import com.invify.services.media.MediaService;
import com.invify.services.transaction.InvitationTransactionService;
import com.invify.services.user.UserService;
import com.invify.utils.ResponseAPIUtil;
import com.invify.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final TemplateRepository templateRepository;
    private final MediaService mediaService;
    private final EventService eventService;
    private final InvitationTransactionService transactionService;
    private final UserService userService;

    @Override
    public APIResponsePageDTO getInvitations(InvitationRequestDTO request) {
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());

        Page<Invitation> invitations = invitationRepository.findInvitationsWithFilter(StringUtils.wildcardWrapper(request.getCoupleName().toLowerCase()),
                StringUtils.wildcardWrapper(request.getCoupleName().toLowerCase()), StringUtils.wildcardWrapper(request.getTemplateName().toLowerCase()), pageRequest);

        Page<InvitationResponseDTO> response = invitations.map(invitation -> InvitationResponseDTO.builder()
                .invitationId(invitation.getInvitationId())
                .coupleName(String.format("%s & %s", invitation.getBrideName(), invitation.getGroomName()))
                .templateName(invitation.getTemplate().getTemplateName())
                .templateCategory(invitation.getTemplate().getTemplateCategory().name())
                .slug(invitation.getSlug())
                .activeStatus(invitation.getActiveStatus())
                .expiredDate(invitation.getExpiredDate())
                .build());


        return ResponseAPIUtil.success(response.getContent(), ReturnCode.DATA_SUCCESSFULLY_FETCHED.getMessage(), invitations.getNumber(), invitations.getTotalElements(), invitations.getTotalPages());
    }

    @Transactional(readOnly = true)
    @Override
    public APIResponseDTO getInvitationDetails(UUID invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(() -> new NotFoundException("Invitation not found"));
        User user = userService.getUserDetailByInvitationId(invitationId);

        TemplateResponseDTO template = getTemplateResponseDTO(invitation);

        List<InvitationMedia> mediaListByInvitation = mediaService.getMediaListByInvitation(invitation);
        List<MediaResponseDTO> mediaResponseDTOList = getMediaResponse(mediaListByInvitation);

        List<InvitationEvent> eventListByInvitation = eventService.getEventListByInvitation(invitation);
        List<EventResponseDTO> eventResponseDTOList = getEventResponse(eventListByInvitation);


        InvitationDetailResponseDTO response = InvitationDetailResponseDTO.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .invitationId(invitation.getInvitationId())
                .subscriptionPlan(user.getSubscriptionPlan().name())
                .brideName(invitation.getBrideName())
                .groomName(invitation.getGroomName())
                .slug(invitation.getSlug())
                .price(invitation.getTemplate().getPrice())
                .activeStatus(invitation.getActiveStatus())
                .expiredDate(invitation.getExpiredDate())
                .template(template)
                .gallery(mediaResponseDTOList)
                .events(eventResponseDTOList)
                .build();

        return ResponseAPIUtil.success(response, ReturnCode.DATA_SUCCESSFULLY_FETCHED.getMessage());
    }

    private static List<EventResponseDTO> getEventResponse(List<InvitationEvent> eventListByInvitation) {

        List<EventResponseDTO> eventResponseDTOList = new ArrayList<>();

        eventListByInvitation.forEach(event -> {
            EventResponseDTO eventResponseDTO = EventResponseDTO.builder()
                    .eventId(event.getEventId())
                    .eventName(event.getEventName())
                    .eventDate(event.getEventDate())
                    .eventEndDate(event.getEventEndDate())
                    .eventLocation(event.getEventLocation())
                    .eventAddress(event.getEventAddress())
                    .eventMapUrl(event.getEventMapUrl())
                    .eventOrder(event.getEventOrder())
                    .build();
            eventResponseDTOList.add(eventResponseDTO);
        });

        return eventResponseDTOList;
    }

    private static TemplateResponseDTO getTemplateResponseDTO(Invitation invitation) {
        return TemplateResponseDTO.builder()
                .templateId(invitation.getTemplate().getTemplateId())
                .templateName(invitation.getTemplate().getTemplateName())
                .hasVideoBackground(invitation.getTemplate().getHasVideoBackground())
                .build();
    }

    private static List<MediaResponseDTO> getMediaResponse(List<InvitationMedia> mediaListByInvitation) {
        List<MediaResponseDTO> mediaResponseDTOList = new ArrayList<>();
        mediaListByInvitation.forEach(media ->  {
            String extension = media.getMediaUrl().substring(media.getMediaUrl().lastIndexOf(".") + 1).toLowerCase();

            MediaResponseDTO mediaResponseDTO = MediaResponseDTO.builder()
                    .mediaId(media.getMediaId())
                    .mediaType(media.getMediaType())
                    .contentType(getContentType(extension))
                    .displayOrder(media.getDisplayOrder())
                    .activeStatus(media.getActiveStatus())
                    .mediaUrl(media.getMediaUrl())
                    .build();
            mediaResponseDTOList.add(mediaResponseDTO);
        });
        return mediaResponseDTOList;
    }

    @Transactional
    @Override
    public APIResponseDTO create(InvitationRequestDTO request) throws JsonProcessingException {
        validationInvitation(request);

        Invitation invitation = createInvitation(request, null, false);
        List<InvitationMedia>  invitationMediaList = mediaService.createMediaList(request, invitation);
        List<InvitationEvent> invitationEventList = eventService.createEvent(request, invitation);
        InvitationTransaction transaction = transactionService.createTransaction(invitation, request);

        invitationRepository.save(invitation);
        mediaService.saveMediaList(invitationMediaList);
        eventService.saveEvent(invitationEventList);
        transactionService.saveTransaction(transaction);


        return ResponseAPIUtil.success(ReturnCode.DATA_SUCCESSFULLY_CREATED.getCode(), ReturnCode.DATA_SUCCESSFULLY_CREATED.getMessage());
    }

    @Override
    public APIResponseDTO edit(InvitationRequestDTO request, UUID invitationId) throws JsonProcessingException {
        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(() -> new NotFoundException("invitation not found"));

        validationInvitation(request);

        mediaService.deleteMediaByInvitationId(request, invitationId, invitation.getSlug());
        eventService.deleteEventByInvitationId(invitationId);

        Invitation newInvitation = createInvitation(request, invitationId, true);
        List<InvitationMedia> newInvitationMediaList = mediaService.createMediaList(request, newInvitation);
        List<InvitationEvent> newInvitationEventList = eventService.createEvent(request, newInvitation);
        InvitationTransaction invitationTransaction = transactionService.editTransaction(newInvitation, request);

        invitationRepository.save(newInvitation);
        mediaService.saveMediaList(newInvitationMediaList);
        eventService.saveEvent(newInvitationEventList);
        transactionService.saveTransaction(invitationTransaction);


        return ResponseAPIUtil.success(ReturnCode.DATA_SUCCESSFULLY_UPDATED.getCode(), ReturnCode.DATA_SUCCESSFULLY_UPDATED.getMessage());
    }

    private void validationInvitation(InvitationRequestDTO request) {
        if (request.getUserId() == null) {
            throw new BadRequestException("user id required");
        }

        if (request.getGallery() == null || request.getGallery().isEmpty()) {
            throw new BadRequestException("gallery required");
        }

        String subscriptionPlan = request.getSubscriptionPlan();
        long sizeGalleryImage = request.getGallery().stream()
                .filter(media -> Objects.requireNonNull(media.getContentType()).startsWith("image")).count();
        boolean anyVideoInGallery = request.getGallery().stream()
                .anyMatch(media -> Objects.requireNonNull(media.getContentType()).startsWith("video"));

        if (subscriptionPlan.equals(SubscriptionPlan.BASIC.name()) && sizeGalleryImage > 8) {
            throw new BadRequestException("You can upload maximum 8 images");
        }

        if (subscriptionPlan.equals(SubscriptionPlan.BASIC.name()) && anyVideoInGallery) {
            throw new BadRequestException("upgrade your plan to upload video");
        }

        Template template = templateRepository.findById(request.getTemplateId()).orElseThrow(() -> new NotFoundException("template not found"));
        if (template.getHasVideoBackground() && request.getVideoBackground() == null) {
            throw new BadRequestException("video background required");
        }

        if (!template.getHasVideoBackground() && request.getVideoBackground() != null) {
            throw new BadRequestException("template doesn't have video background");
        }

        if (template.getHasVideoBackground() && Objects.requireNonNull(request.getVideoBackground().getContentType()).startsWith("image")) {
            throw new BadRequestException("video background must be video");
        }

    }

    private static String getContentType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        return switch (extension) {
            case "png" -> "image/png";
            case "jpg", "jpeg" -> "image/jpeg";
            case "webp" -> "image/webp";
            case "gif" -> "image/gif";
            case "mp4" -> "video/mp4";
            default -> "application/octet-stream";
        };
    }


    private Invitation createInvitation(InvitationRequestDTO request, UUID invitationId, boolean isEdit) {
        Invitation invitation = new Invitation();
        invitation.setInvitationId(isEdit ? invitationId : UUID.randomUUID());
        String[] splitByAnd = request.getCoupleName().split("&");
        if (splitByAnd.length == 2) {
            String firstName = splitByAnd[0].trim().split("\\s+")[0];
            String secondName = splitByAnd[1].trim().split("\\s+")[0];
            String slug = (firstName + "-" + secondName).toLowerCase();
            invitation.setSlug(slug);
        }
        invitation.setBrideName(splitByAnd[0].trim());
        invitation.setGroomName(splitByAnd[1].trim());
        invitation.setTemplate(templateRepository.getReferenceById(request.getTemplateId()));
        invitation.setActiveStatus(1);
        invitation.setCreatedDate(LocalDateTime.now());
        invitation.setExpiredDate(getExpiredDate(request));

        return invitation;
    }

    private LocalDate getExpiredDate(InvitationRequestDTO request) {
        if (request.getSubscriptionPlan().equals(SubscriptionPlan.BASIC.name()) && request.getExpiredDate() != null) {
            if (!request.getExpiredDate().equals(LocalDate.now().plusMonths(3))) {
                return LocalDate.now().plusMonths(3);
            } else {
                return request.getExpiredDate();
            }
        }

        return LocalDate.now().plusMonths(3);
    }
}
