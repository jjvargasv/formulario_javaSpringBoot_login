package com.jjvargas.formulario_nuevo.repository;

import com.jjvargas.formulario_nuevo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
}

