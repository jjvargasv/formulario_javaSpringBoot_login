package com.jjvargas.formulario_nuevo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
    "spring.security.user.name=test",
    "spring.security.user.password=test",
    "spring.security.user.roles=USER",
    "jwt.secret=testSecretKey1234567890123456789012345678901234567890",
    "jwt.expiration=3600000"
})
class FormularioNuevoApplicationTests {

    @Test
    void contextLoads() {
        // Esta prueba verifica que el contexto de Spring se carga correctamente
    }
}
