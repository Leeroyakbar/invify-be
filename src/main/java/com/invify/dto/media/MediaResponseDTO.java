package com.invify.dto.media;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MediaResponseDTO {

    private UUID mediaId;
    private String mediaType;
    private String mediaUrl;
    private String contentType;
    private Integer displayOrder;
    private Integer activeStatus;
}
