package com.talesnunes.Sitexofone_backend.controllers;

import com.talesnunes.Sitexofone_backend.dto.TestimonialRequestDTO;
import com.talesnunes.Sitexofone_backend.dto.TestimonialResponseDTO;
import com.talesnunes.Sitexofone_backend.services.TestimonialService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/testimonials")
public class TestimonialController {

    private final TestimonialService service;

    public TestimonialController(TestimonialService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TestimonialResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestimonialResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TestimonialResponseDTO> create(@RequestBody @Valid TestimonialRequestDTO dto) {
        TestimonialResponseDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/testimonials/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestimonialResponseDTO> update(@PathVariable Long id, @RequestBody @Valid TestimonialRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<TestimonialResponseDTO> approve(@PathVariable Long id) {
        return ResponseEntity.ok(service.approve(id));
    }

    @PatchMapping("/{id}/disapprove")
    public ResponseEntity<TestimonialResponseDTO> disapprove(@PathVariable Long id) {
        return ResponseEntity.ok(service.disapprove(id));
    }
}
