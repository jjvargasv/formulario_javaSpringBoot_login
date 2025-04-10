package com.jjvargas.formulario_nuevo.service;

import com.jjvargas.formulario_nuevo.model.Producto;
import com.jjvargas.formulario_nuevo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService {

    private static final Logger logger = LoggerFactory.getLogger(ProductoService.class);

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarProductos() {
        try {
            logger.info("Intentando listar productos");
            List<Producto> productos = productoRepository.findAll();
            logger.info("Productos encontrados: {}", productos.size());
            return productos;
        } catch (Exception e) {
            logger.error("Error al listar productos: {}", e.getMessage(), e);
            throw new RuntimeException("Error al listar los productos: " + e.getMessage());
        }
    }

    public Producto crearProducto(Producto producto) {
        try {
            logger.info("Intentando crear producto: {}", producto);
            return productoRepository.save(producto);
        } catch (Exception e) {
            logger.error("Error al crear producto: {}", e.getMessage(), e);
            throw new RuntimeException("Error al crear el producto: " + e.getMessage());
        }
    }

    public Producto obtenerProductoPorId(Long id) {
        try {
            logger.info("Intentando obtener producto con ID: {}", id);
            Optional<Producto> producto = productoRepository.findById(id);
            return producto.orElseThrow(() -> {
                logger.warn("Producto no encontrado con ID: {}", id);
                return new RuntimeException("Producto no encontrado con ID: " + id);
            });
        } catch (Exception e) {
            logger.error("Error al obtener producto: {}", e.getMessage(), e);
            throw new RuntimeException("Error al obtener el producto: " + e.getMessage());
        }
    }

    public Producto actualizarProducto(Long id, Producto producto) {
        try {
            logger.info("Intentando actualizar producto con ID: {}", id);
            Producto productoExistente = obtenerProductoPorId(id);
            productoExistente.setNombre(producto.getNombre());
            productoExistente.setDescripcion(producto.getDescripcion());
            productoExistente.setPrecio(producto.getPrecio());
            productoExistente.setStock(producto.getStock());
            return productoRepository.save(productoExistente);
        } catch (Exception e) {
            logger.error("Error al actualizar producto: {}", e.getMessage(), e);
            throw new RuntimeException("Error al actualizar el producto: " + e.getMessage());
        }
    }

    public void eliminarProducto(Long id) {
        try {
            logger.info("Intentando eliminar producto con ID: {}", id);
            productoRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error al eliminar producto: {}", e.getMessage(), e);
            throw new RuntimeException("Error al eliminar el producto: " + e.getMessage());
        }
    }
}