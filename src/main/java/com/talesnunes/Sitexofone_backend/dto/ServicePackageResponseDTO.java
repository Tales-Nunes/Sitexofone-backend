package com.talesnunes.Sitexofone_backend.dto;

import com.talesnunes.Sitexofone_backend.entities.ServicePackage;
import lombok.Data;

@Data
public class ServicePackageResponseDTO {
    private Long id;
    private String name;
    private String duration;
    private Double price;
    private String description;
    private String options;

    public ServicePackageResponseDTO(ServicePackage entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.duration = entity.getDuration();
        this.price = entity.getPrice();
        this.description = entity.getDescription();
        this.options = entity.getOptions();
    }
}
