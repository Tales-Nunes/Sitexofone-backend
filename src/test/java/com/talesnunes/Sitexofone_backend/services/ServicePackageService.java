package com.talesnunes.Sitexofone_backend.services;

import com.talesnunes.Sitexofone_backend.dto.ServicePackageRequestDTO;
import com.talesnunes.Sitexofone_backend.dto.ServicePackageResponseDTO;
import com.talesnunes.Sitexofone_backend.entities.ServicePackage;
import com.talesnunes.Sitexofone_backend.exceptions.ResourceNotFoundException;
import com.talesnunes.Sitexofone_backend.repositories.ServicePackageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicePackageServiceTest {

    @Mock
    private ServicePackageRepository repository;

    @InjectMocks
    private ServicePackageService service;

    private ServicePackage entity;
    private ServicePackageRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        entity = new ServicePackage();
        entity.setId(1L);
        entity.setName("Pacote Premium");
        entity.setDuration("3 horas");
        entity.setPrice(1500.0);
        entity.setDescription("Pacote completo com saxofone e DJ");
        entity.setOptions("Iluminação, som ambiente");

        requestDTO = new ServicePackageRequestDTO();
        requestDTO.setName("Pacote Premium");
        requestDTO.setDuration("3 horas");
        requestDTO.setPrice(1500.0);
        requestDTO.setDescription("Pacote completo com saxofone e DJ");
        requestDTO.setOptions("Iluminação, som ambiente");
    }

    @Test
    void findAll_shouldReturnListOfPackages() {
        when(repository.findAll()).thenReturn(List.of(entity));

        List<ServicePackageResponseDTO> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("Pacote Premium", result.get(0).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnPackage_whenExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        ServicePackageResponseDTO result = service.findById(1L);

        assertNotNull(result);
        assertEquals("Pacote Premium", result.getName());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
        verify(repository, times(1)).findById(99L);
    }

    @Test
    void create_shouldSaveAndReturnResponse() {
        when(repository.save(any(ServicePackage.class))).thenReturn(entity);

        ServicePackageResponseDTO result = service.create(requestDTO);

        assertNotNull(result);
        assertEquals("Pacote Premium", result.getName());
        verify(repository, times(1)).save(any(ServicePackage.class));
    }

    @Test
    void update_shouldModifyExistingPackage() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any(ServicePackage.class))).thenReturn(entity);

        ServicePackageResponseDTO result = service.update(1L, requestDTO);

        assertNotNull(result);
        assertEquals("Pacote Premium", result.getName());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(ServicePackage.class));
    }

    @Test
    void update_shouldThrowException_whenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(99L, requestDTO));
        verify(repository, times(1)).findById(99L);
    }

    @Test
    void delete_shouldRemovePackage_whenExists() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void delete_shouldThrowException_whenNotFound() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(99L));
        verify(repository, never()).deleteById(any());
    }
}
