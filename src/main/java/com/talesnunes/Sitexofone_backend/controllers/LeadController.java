package com.talesnunes.Sitexofone_backend.controllers;


import com.talesnunes.Sitexofone_backend.dto.LeadRequestDTO;
import com.talesnunes.Sitexofone_backend.dto.LeadResponseDTO;
import com.talesnunes.Sitexofone_backend.services.LeadService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/leads")
public class LeadController {

    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @GetMapping
    public ResponseEntity<List<LeadResponseDTO>> findAll() {
        return ResponseEntity.ok(leadService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeadResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(leadService.findById(id));
    }

    @PostMapping
    public ResponseEntity<LeadResponseDTO> create(@RequestBody @Valid LeadRequestDTO dto) {
        LeadResponseDTO created = leadService.create(dto);
        return ResponseEntity.created(URI.create("/leads/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeadResponseDTO> update(@PathVariable Long id, @RequestBody @Valid LeadRequestDTO dto) {
        return ResponseEntity.ok(leadService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        leadService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
