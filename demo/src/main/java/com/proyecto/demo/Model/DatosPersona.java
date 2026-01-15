package com.proyecto.demo.Model;

public class DatosPersona {

    String correo;
    String contrasena;

    public DatosPersona(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public DatosPersona()
    {
        this.correo = "";
        this.contrasena = "";
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

public boolean validarDatosLogIn(String correo, String contrasena) {
    if (correo == null || contrasena == null) return false;
    if (correo.trim().isEmpty() || contrasena.trim().isEmpty()) return false;

    // Admin por defecto (coherente con el Controller)
    if (correo.equals("admin@gmail.com") && contrasena.equals("admin1234")) {
        return true;
    }

    // Validación para clientes registrados
    Cliente cliente = new Cliente(correo, contrasena, "", "");
    return cliente.verificarDatos(correo, contrasena);
}

}

