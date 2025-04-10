package com.jjvargas.formulario_nuevo.repository;

import com.jjvargas.formulario_nuevo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuario por email
    Optional<Usuario> findByEmail(String email);

    // Verificar si existe un usuario con un email específico
    boolean existsByEmail(String email);

    // Buscar usuario por email y contraseña (para el login tradicional)
    // Nota: En una implementación real con Spring Security, esto no se usaría directamente
    // ya que el password se compara con el encoder
    Optional<Usuario> findByEmailAndContraseña(String email, String contraseña);
}