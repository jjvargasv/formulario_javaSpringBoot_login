package com.jjvargas.formulario_nuevo.service;

import com.jjvargas.formulario_nuevo.dto.AuthRequest;
import com.jjvargas.formulario_nuevo.dto.AuthResponse;
import com.jjvargas.formulario_nuevo.dto.RegisterRequest;
import com.jjvargas.formulario_nuevo.model.Usuario;
import com.jjvargas.formulario_nuevo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        var usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .contraseña(passwordEncoder.encode(request.getContraseña()))
                .build();
        usuarioRepository.save(usuario);
        var jwtToken = jwtService.generateToken(usuario);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(usuario);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
} 