package com.talesnunes.Sitexofone_backend.services;

import com.talesnunes.Sitexofone_backend.dto.TestimonialRequestDTO;
import com.talesnunes.Sitexofone_backend.dto.TestimonialResponseDTO;
import com.talesnunes.Sitexofone_backend.entities.Testimonial;
import com.talesnunes.Sitexofone_backend.exceptions.ResourceNotFoundException;
import com.talesnunes.Sitexofone_backend.repositories.TestimonialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestimonialService {

    private final TestimonialRepository repository;

    public TestimonialService(TestimonialRepository repository) {
        this.repository = repository;
    }

    public List<TestimonialResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(TestimonialResponseDTO::new)
                .collect(Collectors.toList());
    }

    public TestimonialResponseDTO findById(Long id) {
        Testimonial testimonial = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Depoimento com ID " + id + " não encontrado."));
        return new TestimonialResponseDTO(testimonial);
    }

    public TestimonialResponseDTO create(TestimonialRequestDTO dto) {
        Testimonial entity = new Testimonial();
        entity.setClient(dto.getClient());
        entity.setText(dto.getText());
        entity.setRating(dto.getRating());
        entity.setApproved(dto.getApproved());
        Testimonial saved = repository.save(entity);
        return new TestimonialResponseDTO(saved);
    }

    public TestimonialResponseDTO update(Long id, TestimonialRequestDTO dto) {
        Testimonial entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Depoimento com ID " + id + " não encontrado."));

        entity.setClient(dto.getClient());
        entity.setText(dto.getText());
        entity.setRating(dto.getRating());
        entity.setApproved(dto.getApproved());

        Testimonial updated = repository.save(entity);
        return new TestimonialResponseDTO(updated);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Depoimento com ID " + id + " não encontrado.");
        }
        repository.deleteById(id);
    }

    public TestimonialResponseDTO approve(Long id) {
        Testimonial entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Depoimento com ID " + id + " não encontrado."));
        entity.setApproved(true);
        return new TestimonialResponseDTO(repository.save(entity));
    }

    public TestimonialResponseDTO disapprove(Long id) {
        Testimonial entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Depoimento com ID " + id + " não encontrado."));
        entity.setApproved(false);
        return new TestimonialResponseDTO(repository.save(entity));
    }
}
