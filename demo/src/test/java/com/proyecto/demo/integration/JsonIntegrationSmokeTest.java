package com.proyecto.demo.integration;

import com.proyecto.demo.ManejadorJSON.ProductoJson;
import com.proyecto.demo.Model.Producto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JsonIntegrationSmokeTest {


    @Test
    void INT_01_guardarProducto_y_listar_no_debe_ser_null() {
        String nombre = "QAInt-" + UUID.randomUUID();

        // OJO: usa el constructor real (nombre, float, int)
        Producto p = new Producto(nombre, 10f, 2);

        ProductoJson.guardarProducto(p);

        assertNotNull(ProductoJson.obtenerProductosTotales());
    }

    @Test
    void INT_02_obtenerProductosTotales_retorna_lista_no_null() {
        assertNotNull(ProductoJson.obtenerProductosTotales());
    }

    @Test
    void INT_03_eliminarProducto_no_revienta() throws Exception {
        String nombre = "QAInt-Del-" + UUID.randomUUID();
        Producto p = new Producto(nombre, 5f, 1);
        ProductoJson.guardarProducto(p);

        assertDoesNotThrow(() -> ProductoJson.eliminarProducto(nombre));
    }
}
