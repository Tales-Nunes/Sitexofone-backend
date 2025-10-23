package com.talesnunes.Sitexofone_backend.dto;

import com.talesnunes.Sitexofone_backend.enums.EventType;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record LeadRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String name,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "O telefone é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "O telefone deve ter 11 dígitos numéricos")
        String telephone,

        @NotNull(message = "A data do evento é obrigatória")
        @FutureOrPresent(message = "A data do evento não pode ser no passado")
        LocalDate dateEvent,

        @NotBlank(message = "O local é obrigatório")
        String local,

        @NotNull(message = "O tipo de evento é obrigatório")
        EventType typeEvent,

        @NotBlank(message = "A mensagem é obrigatória")
        String message
) {}
