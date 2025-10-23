package com.talesnunes.Sitexofone_backend.repositories;

import com.talesnunes.Sitexofone_backend.entities.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeadRepository extends JpaRepository<Lead, Long> {
}
