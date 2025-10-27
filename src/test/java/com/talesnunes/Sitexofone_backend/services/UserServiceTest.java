package com.talesnunes.Sitexofone_backend.services;

import com.talesnunes.Sitexofone_backend.dto.UserRequestDTO;
import com.talesnunes.Sitexofone_backend.dto.UserResponseDTO;
import com.talesnunes.Sitexofone_backend.entities.User;
import com.talesnunes.Sitexofone_backend.enums.UserRole;
import com.talesnunes.Sitexofone_backend.exceptions.EmailAlreadyExistsException;
import com.talesnunes.Sitexofone_backend.exceptions.ResourceNotFoundException;
import com.talesnunes.Sitexofone_backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserRequestDTO request;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setName("Tales");
        user.setEmail("tales@example.com");
        user.setPassword("123");
        user.setRole(UserRole.ADMIN);

        request = new UserRequestDTO();
        request.setName("Tales Updated");
        request.setEmail("tales@example.com");
        request.setPassword("456");
        request.setRole(UserRole.ADMIN);
    }

    @Test
    void create_ShouldSaveUser_WhenEmailIsUnique() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDTO response = userService.create(request);

        assertEquals("Tales", response.getName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void create_ShouldThrowException_WhenEmailExists() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(EmailAlreadyExistsException.class, () -> userService.create(request));
    }

    @Test
    void listAll_ShouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserResponseDTO> result = userService.listAll();

        assertEquals(1, result.size());
        assertEquals("Tales", result.get(0).getName());
    }

    @Test
    void findById_ShouldReturnUser_WhenExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDTO result = userService.findById(1L);

        assertEquals("Tales", result.getName());
    }

    @Test
    void findById_ShouldThrowException_WhenNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findById(99L));
    }

    @Test
    void delete_ShouldCallRepositoryDelete_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.delete(1L);

        verify(userRepository).delete(user);
    }

    @Test
    void update_ShouldUpdateUserData_WhenValid() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("encoded123");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDTO updated = userService.update(1L, request);

        assertEquals("Tales Updated", updated.getName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void update_ShouldThrowException_WhenEmailAlreadyUsedByAnotherUser() {
        User anotherUser = new User();
        anotherUser.setId(2L);
        anotherUser.setEmail("new@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("new@example.com")).thenReturn(Optional.of(anotherUser));

        request.setEmail("new@example.com");

        assertThrows(EmailAlreadyExistsException.class, () -> userService.update(1L, request));
    }

    @Test
    void findEntityById_ShouldReturnUser_WhenExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findEntityById(1L);

        assertEquals(user, result);
    }

    @Test
    void findEntityById_ShouldThrowException_WhenNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findEntityById(1L));
    }
}
