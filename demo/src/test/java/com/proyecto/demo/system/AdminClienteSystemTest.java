package com.proyecto.demo.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.demo.Model.DatosPersona;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas de Sistema (API) - Endpoints /admin y /cliente
 *
 * Nota: En este proyecto los JSON tienen rutas hardcodeadas a C:\Users\MGI\...
 * Por eso preparamos los archivos antes de ejecutar tests.
 */
@SpringBootTest
@AutoConfigureMockMvc
class AdminClienteSystemTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    

    private static void ensureJsonArrayFile(String path) throws Exception {
        Path p = Path.of(path);
        if (!Files.exists(p)) {
            Files.writeString(p, "[]");
            return;
        }
        String content = Files.readString(p).trim();
        if (content.isEmpty()) Files.writeString(p, "[]");
    }

    private static String uniqueEmail() {
        return "qa_" + UUID.randomUUID().toString().replace("-", "") + "@mail.com";
    }

    // -------------------- ADMIN --------------------

    @Test
    void SYS_01_admin_login_ok_con_admin_por_defecto() throws Exception {
        DatosPersona dp = new DatosPersona();
        dp.setCorreo("admin@gmail.com");
        dp.setContrasena("admin1234");

        mvc.perform(post("/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dp)))
                .andExpect(status().isOk())
                .andExpect(content().string("Inicio de sesion valido"));
    }

    @Test
    void SYS_02_admin_login_badrequest_con_credenciales_invalidas() throws Exception {
        DatosPersona dp = new DatosPersona();
        dp.setCorreo("noexiste@mail.com");
        dp.setContrasena("mala");

        mvc.perform(post("/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dp)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Datos invalidos"));
    }

    @Test
    void SYS_03_admin_registro_ok_con_correo_nuevo() throws Exception {
        String correo = uniqueEmail();

        String body = """
        {
          "correo": "%s",
          "contrasena": "1234",
          "nombre": "QA",
          "numeroTelefonico": "04120000001"
        }
        """.formatted(correo);

        mvc.perform(post("/admin/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().string("Datos Ingresados"));
    }

    @Test
    void SYS_04_admin_registro_conflict_si_registras_dos_veces_mismo_correo() throws Exception {
        String correo = uniqueEmail();

        String body = """
        {
          "correo": "%s",
          "contrasena": "1234",
          "nombre": "QA",
          "numeroTelefonico": "04120000002"
        }
        """.formatted(correo);

        mvc.perform(post("/admin/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        mvc.perform(post("/admin/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict())
                .andExpect(content().string("Correo Invalido"));
    }

    @Test
    void SYS_05_admin_agregarProducto_ok_con_datos_validos() throws Exception {
        String body = """
        {
          "nombre": "QAProd-%s",
          "precio": 10,
          "cantidad": 5
        }
        """.formatted(UUID.randomUUID());

        mvc.perform(post("/admin/AgregarProducto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto Agregado"));
    }

   @Test
void SYS_06_admin_agregarProducto_ok_con_otro_producto_valido() throws Exception {
    String body = """
    {
      "nombre": "QAProd-Ok2-%s",
      "precio": 15,
      "cantidad": 10
    }
    """.formatted(UUID.randomUUID());

    mvc.perform(post("/admin/AgregarProducto")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
            .andExpect(status().isOk())
            .andExpect(content().string("Producto Agregado"));
}


    @Test
    void SYS_07_admin_obtenerProductos_ok_despues_de_agregar_uno() throws Exception {
        String body = """
        {
          "nombre": "QAProd-List-%s",
          "precio": 9,
          "cantidad": 3
        }
        """.formatted(UUID.randomUUID());

        mvc.perform(post("/admin/AgregarProducto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        mvc.perform(get("/admin/Productos"))
                .andExpect(status().isOk());
    }

    @Test
    void SYS_08_admin_consultarPerfil_admin_gmail_devuelve_admin() throws Exception {
        mvc.perform(get("/admin/ConsultarPerfil")
                        .param("correo", "admin@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    // -------------------- CLIENTE --------------------

    @Test
    void SYS_09_cliente_crearPedido_badrequest_con_lista_vacia() throws Exception {
        mvc.perform(post("/cliente/crearPedido")
                        .param("correo", "admin@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No se pueden crear pedidos sin diseños"));
    }

    @Test
    void SYS_10_cliente_editarPedido_badrequest_con_lista_vacia() throws Exception {
        mvc.perform(post("/cliente/editarPedido")
                        .param("correo", "admin@gmail.com")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No se pueden cargar pedidos sin diseños"));
    }
}
