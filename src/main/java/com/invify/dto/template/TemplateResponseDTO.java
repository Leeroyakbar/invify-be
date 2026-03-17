package com.invify.dto.template;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemplateResponseDTO {
    private UUID templateId;
    private String templateName;
    private String templateCategory;
    private String previewImage;
    private BigDecimal price;
    private Integer activeStatus;
    private Integer usedCount;
    private LocalDate createdDate;
    private Boolean hasVideoBackground = false;
}
