package com.invify.services.template;

import com.invify.dto.APIResponseDTO;
import com.invify.dto.template.TemplateRequestDTO;
import com.invify.dto.template.TemplateResponseDTO;
import com.invify.entities.Template;
import com.invify.enums.ReturnCode;
import com.invify.enums.TemplateCategory;
import com.invify.repositories.TemplateRepository;
import com.invify.utils.ResponseAPIUtil;
import com.invify.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService{
    private final TemplateRepository templateRepository;

    @Override
    public APIResponseDTO getAllTemplates(TemplateRequestDTO request) {
        String categoryParam = (request.getTemplateCategory() != null && !request.getTemplateCategory().isBlank())
                ? StringUtils.wildcardWrapper(request.getTemplateCategory().toLowerCase())
                : null;

        String templateName = request.getTemplateName() != null ? StringUtils.wildcardWrapper(request.getTemplateName().toLowerCase()) : null;
        List<Template> allTemplate = templateRepository.findAllByTemplateNameOrTemplateCategory(templateName, categoryParam);
//        List<Template> allTemplate = templateRepository.findAllBy();

        List<TemplateResponseDTO> response = allTemplate.stream().map(template -> TemplateResponseDTO.builder()
                .templateId(template.getTemplateId())
                .templateName(template.getTemplateName())
                .templateCategory(template.getTemplateCategory() != null ? template.getTemplateCategory().name() : null)
                .createdDate(template.getCreatedDate().toLocalDate())
                .previewImage(template.getPreviewImage())
                .price(template.getPrice())
                .activeStatus(template.getActiveStatus())
                .usedCount(10)
                .build()
        ).toList();


        return ResponseAPIUtil.success(response, ReturnCode.DATA_SUCCESSFULLY_FETCHED.getMessage());
    }

    @Override
    public APIResponseDTO createTemplate(TemplateRequestDTO request, MultipartFile file) throws IOException {
        String fileName = getFileName(file);
        Path uploadPath = Paths.get("uploads/templates");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        }

        String imageUrl = "/images/templates/" + fileName;

        Template template = Template.builder()
                .templateId(UUID.randomUUID())
                .templateName(request.getTemplateName())
                .previewImage(imageUrl)
                .templateCategory(TemplateCategory.valueOf(request.getTemplateCategory().toUpperCase()))
                .price(request.getPrice())
                .activeStatus(request.getActiveStatus() != null ? request.getActiveStatus() : 1)
                .createdDate(LocalDateTime.now())
                .build();


        templateRepository.save(template);

        return ResponseAPIUtil.success(ReturnCode.DATA_SUCCESSFULLY_CREATED.getCode(), ReturnCode.DATA_SUCCESSFULLY_CREATED.getMessage());
    }

    @Override
    public APIResponseDTO updateTemplate(TemplateRequestDTO request, MultipartFile file) throws IOException {

        Template template = templateRepository.findById(request.getTemplateId()).orElseThrow(() -> new RuntimeException("template not found"));

        if (file != null && !file.isEmpty()) {

            Path uploadPath = Paths.get("uploads/templates");
            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

            if (template.getPreviewImage() != null) {
                String oldFileName = template.getPreviewImage().replace("/images/templates/", "");
                Path oldFilePath = uploadPath.resolve(oldFileName);
                Files.deleteIfExists(oldFilePath);
            }

            String newFileName = getFileName(file);
            Files.copy(file.getInputStream(), uploadPath.resolve(newFileName), StandardCopyOption.REPLACE_EXISTING);

            template.setPreviewImage("/images/templates/" + newFileName);
        }

        template.setTemplateName(request.getTemplateName());
        template.setTemplateCategory(TemplateCategory.valueOf(request.getTemplateCategory().toUpperCase()));
        template.setPrice(request.getPrice());
        template.setUpdatedDate(LocalDateTime.now());
        template.setActiveStatus(request.getActiveStatus() != null ? request.getActiveStatus() : 1);

        templateRepository.save(template);
        return ResponseAPIUtil.success(ReturnCode.DATA_SUCCESSFULLY_UPDATED.getCode(), ReturnCode.DATA_SUCCESSFULLY_UPDATED.getMessage());
    }

    @Transactional
    @Override
    public APIResponseDTO toggleStatus(UUID templateId, Integer status) {
        templateRepository.updateActiveStatus(templateId, status);

        return ResponseAPIUtil.success(ReturnCode.DATA_SUCCESSFULLY_UPDATED.getCode(), ReturnCode.DATA_SUCCESSFULLY_UPDATED.getMessage());
    }

    protected String getFileName(MultipartFile file) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        return timestamp + "_" + file.getOriginalFilename();
    }
}
