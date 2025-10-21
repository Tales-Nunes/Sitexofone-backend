package com.talesnunes.Sitexofone_backend.repositories;

import com.talesnunes.Sitexofone_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
