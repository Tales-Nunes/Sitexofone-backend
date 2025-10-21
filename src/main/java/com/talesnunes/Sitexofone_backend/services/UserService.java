package com.talesnunes.Sitexofone_backend.services;

import com.talesnunes.Sitexofone_backend.dto.UserRequestDTO;
import com.talesnunes.Sitexofone_backend.dto.UserResponseDTO;
import com.talesnunes.Sitexofone_backend.entities.User;
import com.talesnunes.Sitexofone_backend.exceptions.EmailAlreadyExistsException;
import com.talesnunes.Sitexofone_backend.exceptions.ResourceNotFoundException;
import com.talesnunes.Sitexofone_backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private static final String USER_NOT_FOUND = "Usuário com ID %d nao encontrado.";

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponseDTO create(UserRequestDTO dto) {
        validateEmail(dto.getEmail());
        User saved = userRepository.save(convertDtoToUser(dto));
        return new UserResponseDTO(saved);
    }

    private void validateEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException(email);
        }
    }

    private void validateEmailChange(User user, String newEmail) {
        if (!user.getEmail().equals(newEmail) &&
                userRepository.findByEmail(newEmail).isPresent()) {
            throw new EmailAlreadyExistsException(newEmail);
        }
    }

    @Transactional
    public List<UserResponseDTO> listAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

    public User convertDtoToUser(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        return user;
    }

    public void delete(Long id) {
        User user = findEntityById(id);
        userRepository.delete(user);
    }

    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND.formatted(id)));
        return new UserResponseDTO(user);
    }

    public User findEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND.formatted(id)));
    }

    public UserResponseDTO update(Long id, UserRequestDTO dto) {
        User user = findEntityById(id);

        validateEmailChange(user, dto.getEmail());

        updateUserData(user, dto);

        User updated = userRepository.save(user);

        return new UserResponseDTO(updated);
    }

    private void updateUserData(User user, UserRequestDTO dto) {
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        user.setRole(dto.getRole());
    }
}
