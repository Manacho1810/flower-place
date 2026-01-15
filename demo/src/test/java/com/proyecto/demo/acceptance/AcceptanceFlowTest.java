package com.proyecto.demo.acceptance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Aceptación: Given-When-Then sobre reglas de negocio (casos negativos controlados).
 */
@SpringBootTest
@AutoConfigureMockMvc
class AcceptanceFlowTest {

    @Autowired MockMvc mvc;

    @Test
    void ACC_01_dado_sin_disenos_cuando_crea_pedido_entonces_rechaza() throws Exception {
        mvc.perform(post("/cliente/crearPedido")
                        .param("correo", "admin@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No se pueden crear pedidos sin diseños"));
    }

    @Test
    void ACC_02_dado_sin_disenos_cuando_edita_pedido_entonces_rechaza() throws Exception {
        mvc.perform(post("/cliente/editarPedido")
                        .param("correo", "admin@gmail.com")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No se pueden cargar pedidos sin diseños"));
    }
}
