package com.talesnunes.Sitexofone_backend.dto;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class TestimonialRequestDTO {

    @NotBlank(message = "O nome do cliente é obrigatório.")
    private String client;

    @NotBlank(message = "O texto do depoimento é obrigatório.")
    private String text;

    @NotNull(message = "A nota é obrigatória.")
    @Min(value = 1, message = "A nota mínima é 1.")
    @Max(value = 5, message = "A nota máxima é 5.")
    private Integer rating;

    @NotNull(message = "O campo 'approved' é obrigatório.")
    private Boolean approved;
}
