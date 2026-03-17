package com.invify.services.media;

import com.invify.dto.invitation.InvitationRequestDTO;
import com.invify.entities.Invitation;
import com.invify.entities.InvitationMedia;
import com.invify.enums.MediaType;
import com.invify.exceptions.BadRequestException;
import com.invify.repositories.MediaRepository;
import com.invify.services.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaService {
    private final MediaRepository mediaRepository;
    private final FileService fileService;

    public List<InvitationMedia> getMediaListByInvitation(Invitation invitation) {
        return mediaRepository.findAllByInvitation(invitation);
    }

    public List<InvitationMedia> createMediaList(InvitationRequestDTO request, Invitation invitation) {

        InvitationMedia bridePhotoMedia = createMedia(request.getBridePhoto(), null, invitation, "invitations/couples", 1);
        InvitationMedia groomPhotoMedia = createMedia(request.getGroomPhoto(), null, invitation, "invitations/couples", 2);
        InvitationMedia musicBackgroundMedia = createMedia(null, request.getMusicBackground(), invitation, "invitations/music", 3);
        InvitationMedia videoBackgroundMedia = createMedia(request.getVideoBackground(), null, invitation, "invitations/video", 99);

        List<InvitationMedia> invitationMediaList = new ArrayList<>();
        invitationMediaList.add(bridePhotoMedia);
        invitationMediaList.add(groomPhotoMedia);
        invitationMediaList.add(musicBackgroundMedia);
        invitationMediaList.add(videoBackgroundMedia);
        List<MultipartFile> gallery = request.getGallery();

        for (int i = 0; i < gallery.size(); i++) {
            InvitationMedia media = createMedia(gallery.get(i), null, invitation, "invitations/gallery", i + 4);

            invitationMediaList.add(media);
        }
        return invitationMediaList;
    }

    public InvitationMedia createMedia(MultipartFile file, String musicBackground, Invitation invitation, String subFolder, Integer displayOrder) {
        InvitationMedia media = new InvitationMedia();

        media.setMediaId(UUID.randomUUID());
        String mediaUrl;
        String contentType;
        if (file != null) {
            mediaUrl= fileService.saveFile(file, subFolder, invitation.getSlug());
            contentType = file.getContentType();
        } else {
            mediaUrl = musicBackground;
            contentType = "audio";
        }

        if (Objects.requireNonNull(contentType).startsWith("image") && displayOrder == 1) {
            media.setMediaType(MediaType.BRIDE_PHOTO.name());
        } else if (Objects.requireNonNull(contentType).startsWith("image") && displayOrder == 2) {
            media.setMediaType(MediaType.GROOM_PHOTO.name());
        } else if (Objects.requireNonNull(contentType).startsWith("image")) {
            media.setMediaType(MediaType.GALLERY.name());
        } else if (Objects.requireNonNull(contentType).startsWith("video") && displayOrder == 99) {
            media.setMediaType(MediaType.VIDEO_BACKGROUND.name());
        } else if (Objects.requireNonNull(contentType).startsWith("audio")) {
            media.setMediaType(MediaType.BACKGROUND_MUSIC.name());
        } else {
            throw new BadRequestException("invalid media type");
        }
        media.setMediaUrl(mediaUrl);
        media.setDisplayOrder(displayOrder);
        media.setActiveStatus(1);
        media.setCreatedDate(LocalDateTime.now());
        media.setInvitation(invitation);
        return media;
    }

    public void saveMedia(InvitationMedia media) {
        mediaRepository.save(media);
    }

    public void saveMediaList(List<InvitationMedia> mediaList) {
        mediaRepository.saveAll(mediaList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteMediaByInvitationId(InvitationRequestDTO requestDTO, UUID invitationId, String slug) {
        fileService.deleteAllFilesBySlug("invitations/couples", slug);

        mediaRepository.deleteAllByInvitation_InvitationId(invitationId);
    }
}
