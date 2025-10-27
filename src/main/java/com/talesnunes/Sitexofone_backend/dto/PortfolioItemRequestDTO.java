package com.talesnunes.Sitexofone_backend.dto;

import com.talesnunes.Sitexofone_backend.enums.PortfolioType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioItemRequestDTO {

    @NotBlank(message = "O título é obrigatório.")
    private String title;

    @NotNull(message = "O tipo é obrigatório.")
    private PortfolioType type;

    @NotBlank(message = "A URL é obrigatória.")
    private String url;

    private String description;

    private String tags;
}
