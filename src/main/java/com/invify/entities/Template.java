package com.invify.entities;


import com.invify.enums.TemplateCategory;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "MST_TEMPLATES")
public class Template {

    @Id
    @Column(name = "TEMPLATE_ID")
    private UUID templateId;

    @Column(name = "TEMPLATE_NAME", nullable = false)
    private String templateName;

    @Column(name = "PREVIEW_IMAGE")
    private String previewImage;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "TEMPLATE_CATEGORY", nullable = false)
    private TemplateCategory templateCategory;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    @Column(name = "ACTIVE_STATUS", columnDefinition = "integer default 1")
    private Integer activeStatus;

    @Column(name = "HAS_VIDEO_BACKGROUND", columnDefinition = "boolean default false")
    private Boolean hasVideoBackground = false;

}
