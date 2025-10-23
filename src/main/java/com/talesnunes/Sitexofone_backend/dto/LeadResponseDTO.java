package com.talesnunes.Sitexofone_backend.dto;


import com.talesnunes.Sitexofone_backend.entities.Lead;
import com.talesnunes.Sitexofone_backend.enums.EventType;
import com.talesnunes.Sitexofone_backend.enums.Status;

import java.time.LocalDate;

public record LeadResponseDTO(
        Long id,
        String name,
        String email,
        String telephone,
        LocalDate dateEvent,
        String local,
        EventType typeEvent,
        String message,
        Status status,
        LocalDate createdAt
) {
    public LeadResponseDTO(Lead entity) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getTelephone(),
                entity.getDataEvent(),
                entity.getLocal(),
                entity.getTypeEvent(),
                entity.getMessage(),
                entity.getStatus(),
                entity.getCreateAt()
        );
    }
}
