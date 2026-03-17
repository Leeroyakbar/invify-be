package com.invify.controllers.template;

import com.invify.dto.APIResponseDTO;
import com.invify.dto.template.TemplateRequestDTO;
import com.invify.services.template.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/template")
public class TemplateController {

    private final TemplateService templateService;

    @PostMapping("/get-all-templates")
    private APIResponseDTO getALlTemplates(@RequestBody TemplateRequestDTO requestDTO) {
        return templateService.getAllTemplates(requestDTO);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private APIResponseDTO createTemplate(
            @ModelAttribute TemplateRequestDTO request,
            @RequestParam("previewImage") MultipartFile file) throws IOException {
        return templateService.createTemplate(request, file);
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private APIResponseDTO updateTemplate(
            @ModelAttribute TemplateRequestDTO request,
            @RequestParam(value = "previewImage", required = false) MultipartFile file) throws IOException {
        return templateService.updateTemplate(request, file);
    }

    @PatchMapping("/{templateId}/status")
    public APIResponseDTO toggleStatus(@PathVariable("templateId") UUID templateId,
                                       @RequestParam("status") Integer status) {
        return templateService.toggleStatus(templateId, status);
    }


}
