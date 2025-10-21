package com.talesnunes.Sitexofone_backend.dto;

import com.talesnunes.Sitexofone_backend.entities.User;
import com.talesnunes.Sitexofone_backend.enums.UserRole;
import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private UserRole role;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}

