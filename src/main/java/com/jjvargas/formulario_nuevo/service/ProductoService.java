package com.jjvargas.formulario_nuevo.service;

import com.jjvargas.formulario_nuevo.exception.ProductoNotFoundException;
import com.jjvargas.formulario_nuevo.model.Producto;
import com.jjvargas.formulario_nuevo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto crearProducto(Producto producto) {
        // Validaciones básicas
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es requerido");
        }
        return productoRepository.save(producto);
    }

    @Transactional(readOnly = true)
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // Opción 1: Con excepción personalizada
    @Transactional(readOnly = true)
    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
    }

    // Opción 2: Sin excepción personalizada
    /*
    @Transactional(readOnly = true)
    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto con ID " + id + " no encontrado"));
    }
    */

    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        Producto producto = obtenerProductoPorId(id); // Esto ya maneja la no existencia
        
        producto.setNombre(productoActualizado.getNombre());
        producto.setDescripcion(productoActualizado.getDescripcion());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setCategoria(productoActualizado.getCategoria());
        
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductoNotFoundException(id);
            // O alternativamente:
            // throw new RuntimeException("Producto con ID " + id + " no encontrado");
        }
        productoRepository.deleteById(id);
    }
}