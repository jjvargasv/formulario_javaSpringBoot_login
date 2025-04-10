package com.jjvargas.formulario_nuevo.controller;

import com.jjvargas.formulario_nuevo.dto.RegisterRequest;
import com.jjvargas.formulario_nuevo.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    private final AuthService authService;

    public RegistroController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String mostrarFormularioRegistro() {
        return "registro";
    }

    @PostMapping
    public String procesarRegistro(@ModelAttribute RegisterRequest request, RedirectAttributes redirectAttributes) {
        try {
            authService.register(request);
            redirectAttributes.addFlashAttribute("success", "¡Registro exitoso! Por favor inicia sesión.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar: " + e.getMessage());
            return "redirect:/registro";
        }
    }
} 