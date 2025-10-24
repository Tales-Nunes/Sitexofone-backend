package com.talesnunes.Sitexofone_backend.controllers;

import com.talesnunes.Sitexofone_backend.dto.PortfolioItemRequestDTO;
import com.talesnunes.Sitexofone_backend.dto.PortfolioItemResponseDTO;
import com.talesnunes.Sitexofone_backend.services.PortfolioItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioItemController {

    private final PortfolioItemService service;

    public PortfolioItemController(PortfolioItemService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PortfolioItemResponseDTO> create(@Valid @RequestBody PortfolioItemRequestDTO dto) {
        PortfolioItemResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<PortfolioItemResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioItemResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PortfolioItemResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PortfolioItemRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
