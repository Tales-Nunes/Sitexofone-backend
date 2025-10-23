package com.talesnunes.Sitexofone_backend.dto;

import com.talesnunes.Sitexofone_backend.entities.Testimonial;
import lombok.Data;

@Data
public class TestimonialResponseDTO {
    private Long id;
    private String client;
    private String text;
    private Integer rating;
    private Boolean approved;

    public TestimonialResponseDTO(Testimonial entity) {
        this.id = entity.getId();
        this.client = entity.getClient();
        this.text = entity.getText();
        this.rating = entity.getRating();
        this.approved = entity.getApproved();
    }
}
