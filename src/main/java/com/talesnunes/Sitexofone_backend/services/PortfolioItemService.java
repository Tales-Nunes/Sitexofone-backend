package com.talesnunes.Sitexofone_backend.services;

import com.talesnunes.Sitexofone_backend.dto.PortfolioItemRequestDTO;
import com.talesnunes.Sitexofone_backend.dto.PortfolioItemResponseDTO;
import com.talesnunes.Sitexofone_backend.entities.PortfolioItem;
import com.talesnunes.Sitexofone_backend.exceptions.ResourceNotFoundException;
import com.talesnunes.Sitexofone_backend.repositories.PortfolioItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioItemService {

    private final PortfolioItemRepository repository;

    public PortfolioItemService(PortfolioItemRepository repository) {
        this.repository = repository;
    }

    public PortfolioItemResponseDTO create(PortfolioItemRequestDTO dto) {
        PortfolioItem item = new PortfolioItem();
        item.setTitle(dto.getTitle());
        item.setType(dto.getType());
        item.setUrl(dto.getUrl());
        item.setDescription(dto.getDescription());
        item.setTags(dto.getTags());
        PortfolioItem saved = repository.save(item);
        return new PortfolioItemResponseDTO(saved);
    }

    public List<PortfolioItemResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(PortfolioItemResponseDTO::new)
                .toList();
    }

    public PortfolioItemResponseDTO findById(Long id) {
        PortfolioItem item = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item de portfólio não encontrado com ID " + id));
        return new PortfolioItemResponseDTO(item);
    }

    public PortfolioItemResponseDTO update(Long id, PortfolioItemRequestDTO dto) {
        PortfolioItem item = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item de portfólio não encontrado com ID " + id));
        item.setTitle(dto.getTitle());
        item.setType(dto.getType());
        item.setUrl(dto.getUrl());
        item.setDescription(dto.getDescription());
        item.setTags(dto.getTags());
        PortfolioItem updated = repository.save(item);
        return new PortfolioItemResponseDTO(updated);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Item de portfólio não encontrado com ID " + id);
        }
        repository.deleteById(id);
    }
}
