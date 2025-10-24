package com.talesnunes.Sitexofone_backend.security;

import com.talesnunes.Sitexofone_backend.dto.AuthenticationRequest;
import com.talesnunes.Sitexofone_backend.dto.AuthenticationResponse;
import com.talesnunes.Sitexofone_backend.dto.RegisterRequest;
import com.talesnunes.Sitexofone_backend.entities.User;
import com.talesnunes.Sitexofone_backend.enums.UserRole;
import com.talesnunes.Sitexofone_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegisterRequest request) {
        var user = new User();
        user.setName(request.getNome());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getSenha()));
        user.setRole(UserRole.ADMIN);
        repository.save(user);

        var userDetails = new CustomUserDetails(user);
        var jwtToken = jwtService.generateToken(userDetails);

        return new AuthenticationResponse(jwtToken);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        // autentica usando Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );

        // busca o usuário no banco
        var user = repository.findByEmail(request.getEmail()).orElseThrow();

        // cria UserDetails para gerar o token
        var userDetails = new CustomUserDetails(user);

        // gera JWT
        var jwtToken = jwtService.generateToken(userDetails);

        return new AuthenticationResponse(jwtToken);
    }

}
