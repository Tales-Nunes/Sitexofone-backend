package com.talesnunes.Sitexofone_backend.dto;

import com.talesnunes.Sitexofone_backend.entities.PortfolioItem;
import com.talesnunes.Sitexofone_backend.enums.PortfolioType;
import lombok.Data;

import java.util.Date;

@Data
public class PortfolioItemResponseDTO {
    private Long id;
    private String title;
    private PortfolioType type;
    private String url;
    private String description;
    private String tags;
    private Date createdAt;

    public PortfolioItemResponseDTO(PortfolioItem entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.type = entity.getType();
        this.url = entity.getUrl();
        this.description = entity.getDescription();
        this.tags = entity.getTags();
        this.createdAt = entity.getCreatedAt();
    }
}
