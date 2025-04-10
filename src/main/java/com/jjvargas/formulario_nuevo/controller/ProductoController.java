package com.jjvargas.formulario_nuevo.controller;

import com.jjvargas.formulario_nuevo.model.Producto;
import com.jjvargas.formulario_nuevo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String listarProductos(Model model) {
        try {
            model.addAttribute("productos", productoService.listarProductos());
            return "productos/lista";
        } catch (Exception e) {
            logger.error("Error al listar productos: {}", e.getMessage(), e);
            model.addAttribute("error", "Error al cargar la lista de productos: " + e.getMessage());
            return "error/error";
        }
    }

    @GetMapping("/crear")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("producto", new Producto());
        return "productos/crear";
    }

    @PostMapping("/crear")
    public String crearProducto(@ModelAttribute Producto producto, RedirectAttributes redirectAttributes) {
        try {
            productoService.crearProducto(producto);
            redirectAttributes.addFlashAttribute("success", "Producto creado exitosamente");
            return "redirect:/productos";
        } catch (Exception e) {
            logger.error("Error al crear producto: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "Error al crear el producto: " + e.getMessage());
            return "redirect:/productos/crear";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        try {
            Producto producto = productoService.obtenerProductoPorId(id);
            if (producto == null) {
                model.addAttribute("error", "Producto no encontrado");
                return "error/error";
            }
            model.addAttribute("producto", producto);
            return "productos/editar";
        } catch (Exception e) {
            logger.error("Error al cargar producto para edición: {}", e.getMessage(), e);
            model.addAttribute("error", "Error al cargar el producto: " + e.getMessage());
            return "error/error";
        }
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarProducto(@PathVariable Long id, @ModelAttribute Producto producto, 
                                   RedirectAttributes redirectAttributes) {
        try {
            productoService.actualizarProducto(id, producto);
            redirectAttributes.addFlashAttribute("success", "Producto actualizado exitosamente");
            return "redirect:/productos";
        } catch (Exception e) {
            logger.error("Error al actualizar producto: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el producto: " + e.getMessage());
            redirectAttributes.addFlashAttribute("producto", producto);
            return "redirect:/productos/editar/" + id;
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productoService.eliminarProducto(id);
            redirectAttributes.addFlashAttribute("success", "Producto eliminado exitosamente");
        } catch (Exception e) {
            logger.error("Error al eliminar producto: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el producto: " + e.getMessage());
        }
        return "redirect:/productos";
    }
}