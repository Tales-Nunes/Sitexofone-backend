package com.talesnunes.Sitexofone_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ServicePackageRequestDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @NotBlank(message = "A duração é obrigatória.")
    private String duration;

    @NotNull(message = "O preço é obrigatório.")
    @Positive(message = "O preço deve ser maior que zero.")
    private Double price;

    @NotBlank(message = "A descrição é obrigatória.")
    private String description;

    @NotBlank(message = "As opções são obrigatórias.")
    private String options;
}
