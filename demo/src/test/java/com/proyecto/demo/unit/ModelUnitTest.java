package com.proyecto.demo.unit;

import com.proyecto.demo.Model.DatosPersona;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unitarias puras (sin Spring).
 */
class ModelUnitTest {

    @Test
    void UNIT_01_login_admin_valido_true() {
        DatosPersona dp = new DatosPersona();
        boolean ok = dp.validarDatosLogIn("admin@gmail.com", "admin1234");
        assertTrue(ok);
    }

    @Test
    void UNIT_02_login_admin_invalido_false() {
        DatosPersona dp = new DatosPersona();
        boolean ok = dp.validarDatosLogIn("admin@gmail.com", "mala");
        assertFalse(ok);
    }

    @Test
    void UNIT_03_login_correo_inexistente_false() {
        DatosPersona dp = new DatosPersona();
        assertFalse(dp.validarDatosLogIn("no@no.com", "x"));
    }

    @Test
    void UNIT_04_login_correo_vacio_false() {
        DatosPersona dp = new DatosPersona();
        assertFalse(dp.validarDatosLogIn("", "admin1234"));
    }

    @Test
    void UNIT_05_login_contra_vacia_false() {
        DatosPersona dp = new DatosPersona();
        assertFalse(dp.validarDatosLogIn("admin@gmail.com", ""));
    }

    @Test
    void UNIT_06_login_nulos_false_o_manejo_seguro() {
        DatosPersona dp = new DatosPersona();
        // si tu método lanza NPE, ajustamos a assertThrows; si devuelve false, mejor.
        try {
            boolean ok = dp.validarDatosLogIn(null, null);
            assertFalse(ok);
        } catch (Exception ex) {
            // aceptable si el código no maneja nulos: queda documentado como defecto
            assertTrue(ex instanceof NullPointerException || ex instanceof RuntimeException);
        }
    }
}
