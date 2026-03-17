package com.invify.dto.template;

import com.invify.dto.PageableRequest;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemplateRequestDTO extends PageableRequest {

    private UUID templateId;
    private String templateName;
    private String templateCategory;
    private Integer activeStatus;
    private BigDecimal price;

}
