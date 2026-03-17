package com.invify.services.template;

import com.invify.dto.APIResponseDTO;
import com.invify.dto.template.TemplateRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface TemplateService {

    APIResponseDTO getAllTemplates(TemplateRequestDTO request);

    APIResponseDTO createTemplate(TemplateRequestDTO request, MultipartFile file) throws IOException;

    APIResponseDTO updateTemplate(TemplateRequestDTO request, MultipartFile file) throws IOException;

    APIResponseDTO toggleStatus(UUID templateId, Integer status);
}
