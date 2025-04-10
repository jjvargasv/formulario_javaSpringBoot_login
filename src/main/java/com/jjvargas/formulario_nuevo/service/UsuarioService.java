package com.jjvargas.formulario_nuevo.service;

import com.jjvargas.formulario_nuevo.exception.UsuarioExistenteException;
import com.jjvargas.formulario_nuevo.exception.CredencialesInvalidasException;
import com.jjvargas.formulario_nuevo.model.Usuario;
import com.jjvargas.formulario_nuevo.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioService(UsuarioRepository usuarioRepository, 
                         PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrarUsuario(Usuario usuario) {
        validarUsuario(usuario);
        
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new UsuarioExistenteException("El email " + usuario.getEmail() + " ya está registrado");
        }
        
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        return usuarioRepository.save(usuario);
    }

    public Usuario login(String email, String contraseña) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new CredencialesInvalidasException("Credenciales inválidas"));
                
        if (!passwordEncoder.matches(contraseña, usuario.getContraseña())) {
            throw new CredencialesInvalidasException("Credenciales inválidas");
        }
        
        return usuario;
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email es requerido");
        }
        
        if (usuario.getContraseña() == null || usuario.getContraseña().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida");
        }
        
        if (usuario.getContraseña().length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }
    }
}