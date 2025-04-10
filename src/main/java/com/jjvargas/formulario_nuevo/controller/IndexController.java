package com.jjvargas.formulario_nuevo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String mostrarPaginaPrincipal() {
        return "index";
    }
} 