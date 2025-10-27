package com.talesnunes.Sitexofone_backend.services;

import com.talesnunes.Sitexofone_backend.dto.LeadRequestDTO;
import com.talesnunes.Sitexofone_backend.dto.LeadResponseDTO;
import com.talesnunes.Sitexofone_backend.entities.Lead;
import com.talesnunes.Sitexofone_backend.enums.EventType;
import com.talesnunes.Sitexofone_backend.enums.Status;
import com.talesnunes.Sitexofone_backend.exceptions.ResourceNotFoundException;
import com.talesnunes.Sitexofone_backend.repositories.LeadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LeadServiceTest {

    @Mock
    private LeadRepository leadRepository;

    @InjectMocks
    private LeadService leadService;

    private Lead lead;
    private LeadRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        lead = Lead.builder()
                .id(1L)
                .name("Tales Nunes")
                .email("tales@example.com")
                .telephone("99999-9999")
                .dataEvent(LocalDate.of(2025, 10, 23))
                .local("São Paulo")
                .typeEvent(EventType.CERIMONIA)
                .message("Gostaria de um orçamento")
                .status(Status.PENDENTE)
                .build();

        requestDTO = new LeadRequestDTO(
                "Tales Nunes",
                "tales@example.com",
                "99999-9999",
                LocalDate.of(2025, 10, 23),
                "São Paulo",
                EventType.CERIMONIA,
                "Gostaria de um orçamento"
        );
    }

    @Test
    void findAll_shouldReturnListOfLeads() {
        when(leadRepository.findAll()).thenReturn(List.of(lead));

        List<LeadResponseDTO> result = leadService.findAll();

        assertEquals(1, result.size());
        assertEquals("Tales Nunes", result.get(0).name());
        verify(leadRepository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnLeadResponseDTO() {
        when(leadRepository.findById(1L)).thenReturn(Optional.of(lead));

        LeadResponseDTO result = leadService.findById(1L);

        assertEquals("Tales Nunes", result.name());
        verify(leadRepository, times(1)).findById(1L);
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        when(leadRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> leadService.findById(99L));
        verify(leadRepository, times(1)).findById(99L);
    }

    @Test
    void create_shouldSaveAndReturnLeadResponseDTO() {
        when(leadRepository.save(any(Lead.class))).thenReturn(lead);

        LeadResponseDTO result = leadService.create(requestDTO);

        assertNotNull(result);
        assertEquals("Tales Nunes", result.name());
        verify(leadRepository, times(1)).save(any(Lead.class));
    }

    @Test
    void update_shouldModifyExistingLead() {
        when(leadRepository.findById(1L)).thenReturn(Optional.of(lead));
        when(leadRepository.save(any(Lead.class))).thenReturn(lead);

        LeadResponseDTO result = leadService.update(1L, requestDTO);

        assertEquals("Tales Nunes", result.name());
        verify(leadRepository, times(1)).findById(1L);
        verify(leadRepository, times(1)).save(any(Lead.class));
    }

    @Test
    void update_shouldThrowException_whenLeadNotFound() {
        when(leadRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> leadService.update(99L, requestDTO));
        verify(leadRepository, times(1)).findById(99L);
    }

    @Test
    void delete_shouldDeleteLead_whenExists() {
        when(leadRepository.existsById(1L)).thenReturn(true);

        leadService.delete(1L);

        verify(leadRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_shouldThrowException_whenLeadDoesNotExist() {
        when(leadRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> leadService.delete(99L));
        verify(leadRepository, never()).deleteById(any());
    }
}
