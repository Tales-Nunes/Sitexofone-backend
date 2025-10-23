package com.talesnunes.Sitexofone_backend.repositories;

import com.talesnunes.Sitexofone_backend.entities.PortfolioItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioItemRepository extends JpaRepository<PortfolioItem, Long> {
}
