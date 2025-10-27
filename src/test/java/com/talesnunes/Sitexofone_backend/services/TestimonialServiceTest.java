package com.talesnunes.Sitexofone_backend.services;

import com.talesnunes.Sitexofone_backend.dto.TestimonialRequestDTO;
import com.talesnunes.Sitexofone_backend.dto.TestimonialResponseDTO;
import com.talesnunes.Sitexofone_backend.entities.Testimonial;
import com.talesnunes.Sitexofone_backend.exceptions.ResourceNotFoundException;
import com.talesnunes.Sitexofone_backend.repositories.TestimonialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TestimonialServiceTest {

    @Mock
    private TestimonialRepository repository;

    @InjectMocks
    private TestimonialService service;

    private Testimonial testimonial;
    private TestimonialRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testimonial = new Testimonial();
        testimonial.setId(1L);
        testimonial.setClient("Maria");
        testimonial.setText("Excelente trabalho!");
        testimonial.setRating(5);
        testimonial.setApproved(false);

        requestDTO = new TestimonialRequestDTO();
        requestDTO.setClient("Maria");
        requestDTO.setText("Excelente trabalho!");
        requestDTO.setRating(5);
        requestDTO.setApproved(false);
    }

    // ✅ findAll()
    @Test
    void shouldReturnAllTestimonials() {
        when(repository.findAll()).thenReturn(Arrays.asList(testimonial));

        List<TestimonialResponseDTO> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("Maria", result.get(0).getClient());
        verify(repository, times(1)).findAll();
    }

    // ✅ findById() - sucesso
    @Test
    void shouldReturnTestimonialById() {
        when(repository.findById(1L)).thenReturn(Optional.of(testimonial));

        TestimonialResponseDTO result = service.findById(1L);

        assertEquals("Maria", result.getClient());
        verify(repository, times(1)).findById(1L);
    }

    // ❌ findById() - não encontrado
    @Test
    void shouldThrowExceptionWhenTestimonialNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
        verify(repository, times(1)).findById(99L);
    }

    // ✅ create()
    @Test
    void shouldCreateTestimonial() {
        when(repository.save(any(Testimonial.class))).thenReturn(testimonial);

        TestimonialResponseDTO result = service.create(requestDTO);

        assertEquals("Maria", result.getClient());
        verify(repository, times(1)).save(any(Testimonial.class));
    }

    // ✅ update() - sucesso
    @Test
    void shouldUpdateTestimonial() {
        when(repository.findById(1L)).thenReturn(Optional.of(testimonial));
        when(repository.save(any(Testimonial.class))).thenReturn(testimonial);

        TestimonialResponseDTO result = service.update(1L, requestDTO);

        assertEquals("Maria", result.getClient());
        verify(repository, times(1)).save(any(Testimonial.class));
    }

    // ❌ update() - não encontrado
    @Test
    void shouldThrowExceptionWhenUpdatingNonexistentTestimonial() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(99L, requestDTO));
        verify(repository, never()).save(any(Testimonial.class));
    }

    // ✅ delete() - sucesso
    @Test
    void shouldDeleteTestimonial() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    // ❌ delete() - não encontrado
    @Test
    void shouldThrowExceptionWhenDeletingNonexistentTestimonial() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(99L));
        verify(repository, never()).deleteById(99L);
    }

    // ✅ approve()
    @Test
    void shouldApproveTestimonial() {
        when(repository.findById(1L)).thenReturn(Optional.of(testimonial));
        when(repository.save(any(Testimonial.class))).thenReturn(testimonial);

        TestimonialResponseDTO result = service.approve(1L);

        assertTrue(result.getApproved());
        verify(repository, times(1)).save(any(Testimonial.class));
    }

    // ✅ disapprove()
    @Test
    void shouldDisapproveTestimonial() {
        testimonial.setApproved(true);
        when(repository.findById(1L)).thenReturn(Optional.of(testimonial));
        when(repository.save(any(Testimonial.class))).thenReturn(testimonial);

        TestimonialResponseDTO result = service.disapprove(1L);

        assertFalse(result.getApproved());
        verify(repository, times(1)).save(any(Testimonial.class));
    }
}
