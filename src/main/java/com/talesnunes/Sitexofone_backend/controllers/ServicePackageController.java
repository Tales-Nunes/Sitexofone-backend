package com.talesnunes.Sitexofone_backend.controllers;

import com.talesnunes.Sitexofone_backend.dto.ServicePackageRequestDTO;
import com.talesnunes.Sitexofone_backend.dto.ServicePackageResponseDTO;
import com.talesnunes.Sitexofone_backend.services.ServicePackageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/service-packages")
public class ServicePackageController {

    private final ServicePackageService service;

    public ServicePackageController(ServicePackageService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ServicePackageResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicePackageResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ServicePackageResponseDTO> create(@RequestBody @Valid ServicePackageRequestDTO dto) {
        ServicePackageResponseDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/service-packages/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicePackageResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ServicePackageRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
