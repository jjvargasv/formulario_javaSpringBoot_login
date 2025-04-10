package com.jjvargas.formulario_nuevo.controller;

import com.jjvargas.formulario_nuevo.model.Producto;
import com.jjvargas.formulario_nuevo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String listarProductos(Model model, Principal principal) {
        // Obtener información del usuario autenticado
        String username = principal != null ? principal.getName() : "Invitado";
        model.addAttribute("username", username);
        
        // Obtener lista de productos
        List<Producto> productos = productoService.listarProductos();
        model.addAttribute("productos", productos);
        
        return "productos/lista";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCreacion(Model model, Principal principal) {
        // Verificar autenticación
        if (principal == null) {
            return "redirect:/auth/login";
        }
        
        model.addAttribute("producto", new Producto());
        return "productos/crear";
    }

    @PostMapping("/crear")
    public String crearProducto(@ModelAttribute Producto producto, 
                              RedirectAttributes redirectAttributes,
                              Principal principal) {
        // Verificar autenticación
        if (principal == null) {
            return "redirect:/auth/login";
        }
        
        try {
            productoService.crearProducto(producto);
            redirectAttributes.addFlashAttribute("success", "Producto creado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear el producto: " + e.getMessage());
        }
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, 
                                         Model model,
                                         Principal principal) {
        // Verificar autenticación
        if (principal == null) {
            return "redirect:/auth/login";
        }
        
        try {
            Producto producto = productoService.obtenerProductoPorId(id);
            model.addAttribute("producto", producto);
            return "productos/editar";
        } catch (Exception e) {
            return "redirect:/productos";
        }
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarProducto(@PathVariable Long id,
                                   @ModelAttribute Producto producto,
                                   RedirectAttributes redirectAttributes,
                                   Principal principal) {
        // Verificar autenticación
        if (principal == null) {
            return "redirect:/auth/login";
        }
        
        try {
            productoService.actualizarProducto(id, producto);
            redirectAttributes.addFlashAttribute("success", "Producto actualizado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el producto: " + e.getMessage());
        }
        return "redirect:/productos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal) {
        // Verificar autenticación
        if (principal == null) {
            return "redirect:/auth/login";
        }
        
        try {
            productoService.eliminarProducto(id);
            redirectAttributes.addFlashAttribute("success", "Producto eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el producto: " + e.getMessage());
        }
        return "redirect:/productos";
    }
}