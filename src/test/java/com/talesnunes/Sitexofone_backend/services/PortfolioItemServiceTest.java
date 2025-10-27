package com.talesnunes.Sitexofone_backend.services;

import com.talesnunes.Sitexofone_backend.dto.PortfolioItemRequestDTO;
import com.talesnunes.Sitexofone_backend.dto.PortfolioItemResponseDTO;
import com.talesnunes.Sitexofone_backend.entities.PortfolioItem;
import com.talesnunes.Sitexofone_backend.enums.PortfolioType;
import com.talesnunes.Sitexofone_backend.exceptions.ResourceNotFoundException;
import com.talesnunes.Sitexofone_backend.repositories.PortfolioItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PortfolioItemServiceTest {

    @Mock
    private PortfolioItemRepository repository;

    @InjectMocks
    private PortfolioItemService service;

    private PortfolioItem item;
    private PortfolioItemRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        item = new PortfolioItem();
        item.setId(1L);
        item.setTitle("Apresentação Musical");
        item.setType(PortfolioType.VIDEO);
        item.setUrl("https://exemplo.com/video.mp4");
        item.setDescription("Show ao vivo em São Paulo");
        item.setTags("música, ao vivo");

        requestDTO = new PortfolioItemRequestDTO();
        requestDTO.setTitle("Apresentação Musical");
        requestDTO.setType(PortfolioType.VIDEO);
        requestDTO.setUrl("https://exemplo.com/video.mp4");
        requestDTO.setDescription("Show ao vivo em São Paulo");
        requestDTO.setTags("música, ao vivo");
    }

    @Test
    void create_shouldSaveAndReturnResponseDTO() {
        when(repository.save(any(PortfolioItem.class))).thenReturn(item);

        PortfolioItemResponseDTO result = service.create(requestDTO);

        assertNotNull(result);
        assertEquals("Apresentação Musical", result.getTitle());
        verify(repository, times(1)).save(any(PortfolioItem.class));
    }

    @Test
    void findAll_shouldReturnListOfPortfolioItems() {
        when(repository.findAll()).thenReturn(List.of(item));

        List<PortfolioItemResponseDTO> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("Apresentação Musical", result.get(0).getTitle());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnResponseDTO_whenFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(item));

        PortfolioItemResponseDTO result = service.findById(1L);

        assertNotNull(result);
        assertEquals("Apresentação Musical", result.getTitle());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
        verify(repository, times(1)).findById(99L);
    }

    @Test
    void update_shouldModifyExistingItem() {
        when(repository.findById(1L)).thenReturn(Optional.of(item));
        when(repository.save(any(PortfolioItem.class))).thenReturn(item);

        PortfolioItemResponseDTO result = service.update(1L, requestDTO);

        assertEquals("Apresentação Musical", result.getTitle());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(PortfolioItem.class));
    }

    @Test
    void update_shouldThrowException_whenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(99L, requestDTO));
        verify(repository, times(1)).findById(99L);
    }

    @Test
    void delete_shouldRemoveItem_whenExists() {
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
