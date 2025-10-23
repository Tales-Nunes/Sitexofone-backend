package com.talesnunes.Sitexofone_backend.services;

import com.talesnunes.Sitexofone_backend.dto.LeadRequestDTO;
import com.talesnunes.Sitexofone_backend.dto.LeadResponseDTO;
import com.talesnunes.Sitexofone_backend.entities.Lead;
import com.talesnunes.Sitexofone_backend.enums.Status;
import com.talesnunes.Sitexofone_backend.exceptions.ResourceNotFoundException;
import com.talesnunes.Sitexofone_backend.repositories.LeadRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LeadService {

    private final LeadRepository leadRepository;

    public LeadService(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @Transactional(readOnly = true)
    public List<LeadResponseDTO> findAll() {
        return leadRepository.findAll().stream()
                .map(LeadResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public LeadResponseDTO findById(Long id) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lead com ID " + id + " não encontrado"));
        return new LeadResponseDTO(lead);
    }

    @Transactional
    public LeadResponseDTO create(LeadRequestDTO dto) {
        Lead lead = Lead.builder()
                .name(dto.name())
                .email(dto.email())
                .telephone(dto.telephone())
                .dataEvent(dto.dateEvent())
                .local(dto.local())
                .typeEvent(dto.typeEvent())
                .message(dto.message())
                .status(Status.PENDENTE)
                .build();

        Lead saved = leadRepository.save(lead);
        return new LeadResponseDTO(saved);
    }

    @Transactional
    public LeadResponseDTO update(Long id, LeadRequestDTO dto) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lead com ID " + id + " não encontrado"));

        lead.setName(dto.name());
        lead.setEmail(dto.email());
        lead.setTelephone(dto.telephone());
        lead.setDataEvent(dto.dateEvent());
        lead.setLocal(dto.local());
        lead.setTypeEvent(dto.typeEvent());
        lead.setMessage(dto.message());

        Lead updated = leadRepository.save(lead);
        return new LeadResponseDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!leadRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lead com ID " + id + " não encontrado");
        }
        leadRepository.deleteById(id);
    }
}
