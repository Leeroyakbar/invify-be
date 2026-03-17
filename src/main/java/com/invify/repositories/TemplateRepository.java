package com.invify.repositories;

import com.invify.entities.Template;
import com.invify.enums.TemplateCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TemplateRepository extends JpaRepository<Template, UUID> {

    List<Template> findAllBy();

    @Query(value = "SELECT * FROM MST_TEMPLATES mt " +
            "WHERE (:templateName IS NULL OR :templateName = '' OR lower(mt.TEMPLATE_NAME) like :templateName) " +
            "OR (:templateCategory IS NULL OR :templateCategory = '' OR lower(mt.TEMPLATE_CATEGORY) like :templateCategory) ", nativeQuery = true)
    List<Template> findAllByTemplateNameOrTemplateCategory(
            @Param("templateName") String templateName,
            @Param("templateCategory") String templateCategory);

    @Modifying
    @Query("update Template t set t.activeStatus = :activeStatus where t.templateId = :templateId")
    void updateActiveStatus(@Param("templateId") UUID templateId,
                            @Param("activeStatus") Integer activeStatus);
}
