package com.talesnunes.Sitexofone_backend.services;

import com.talesnunes.Sitexofone_backend.dto.ServicePackageRequestDTO;
import com.talesnunes.Sitexofone_backend.dto.ServicePackageResponseDTO;
import com.talesnunes.Sitexofone_backend.entities.ServicePackage;
import com.talesnunes.Sitexofone_backend.repositories.ServicePackageRepository;
import com.talesnunes.Sitexofone_backend.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicePackageService {

    private final ServicePackageRepository repository;

    public ServicePackageService(ServicePackageRepository repository) {
        this.repository = repository;
    }

    public List<ServicePackageResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(ServicePackageResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ServicePackageResponseDTO findById(Long id) {
        ServicePackage servicePackage = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pacote com ID " + id + " não encontrado."));
        return new ServicePackageResponseDTO(servicePackage);
    }

    public ServicePackageResponseDTO create(ServicePackageRequestDTO dto) {
        ServicePackage entity = new ServicePackage();
        entity.setName(dto.getName());
        entity.setDuration(dto.getDuration());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        entity.setOptions(dto.getOptions());
        ServicePackage saved = repository.save(entity);
        return new ServicePackageResponseDTO(saved);
    }

    public ServicePackageResponseDTO update(Long id, ServicePackageRequestDTO dto) {
        ServicePackage entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pacote com ID " + id + " não encontrado."));

        entity.setName(dto.getName());
        entity.setDuration(dto.getDuration());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        entity.setOptions(dto.getOptions());

        ServicePackage updated = repository.save(entity);
        return new ServicePackageResponseDTO(updated);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Pacote com ID " + id + " não encontrado.");
        }
        repository.deleteById(id);
    }
}
