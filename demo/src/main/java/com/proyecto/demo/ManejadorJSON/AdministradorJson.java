package com.proyecto.demo.ManejadorJSON;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.proyecto.demo.Model.Administrador;
import com.proyecto.demo.Model.Pedido;
import com.proyecto.demo.Model.Producto;


/*public class AdministradorJson extends Administrador {
    public AdministradorJson() {
        super(1);
    }   
}*/

    public class AdministradorJson extends Administrador {
            private static final String BASE =
            System.getProperty("user.dir")
            + File.separator + "src" + File.separator + "main" + File.separator + "java"
            + File.separator + "com" + File.separator + "proyecto"
            + File.separator + "demo" + File.separator + "Json";

    private static final String ADMIN_PATH =
            BASE + File.separator + "administrador.json";

        public AdministradorJson(String correo, String contrasena, String nombre, String numeroTelefonico, ArrayList<Producto> productos, ArrayList<Pedido> pedidos) {
            super();
        }

   /////////////////////////////////////////////////////OBTENER/EXTRAER//////////////////////////////////////////

    //FUNCIONAL
    //PRIMERA FORMA DE OBTENER OBJETO ADMIN
    //En esta forma no se toma en cuenta la opcion de que este vacio el JSON, solo extrae un objeto y lo guarda en administrador
    // static public Administrador obtenerAdmin() throws IOException {
    //     Gson gson = new Gson();
    //     JsonReader reader = new JsonReader(new FileReader("src//main//java//com//proyecto//demo//Json//administrador.json"));
    //     //Guardo lo que extraje del json en un objeto administrador
    //     Administrador administrador = gson.fromJson(reader, Administrador.class);
    //     //Lo imprimo
    //     System.out.println("    Nombre del Admin: " + administrador.getNombre());
    //     System.out.println("    Correo del Admin: " + administrador.getCorreo());
    //     System.out.println("    Contraseña: " + administrador.getContrasena());
    //     System.out.println("    Numero de Telefonico: " + administrador.getNumeroTelefonico());
    //     return administrador;
    // }

    //FUNCIONAL
    //SEGUNDA FORMA DE OBTENER OBJETO ADMIN
    //En esta forma si se toma en cuenta la excepcion de que el json este vacio
    static public Administrador obtenerAdmin() throws IOException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(ADMIN_PATH));
        try {
            Administrador administrador = gson.fromJson(reader, Administrador.class);
            System.out.println("    Nombre del Admin: " + administrador.getNombre());
            System.out.println("    Correo del Admin: " + administrador.getCorreo());
            System.out.println("    Contraseña: " + administrador.getContrasena());
            System.out.println("    Numero de Telefonico: " + administrador.getNumeroTelefonico());
            reader.close(); //Cierro el archivo
            return administrador;
        } catch (JsonSyntaxException e) {
            System.err.println("El archivo JSON está vacío o tiene un formato inválido");
            return null;
        }
    }

    //FUNCIONAL
    //TERCERA FORMA DE OBTENER OBJETO ADMIN
    //En esta forma utilizamos en Json de Admin como si fuera una lista de Administradores, y se debe buscar por el campo clave para evitar errores (a pesar de ser uno solo )
    //En este caso es importante que se modifique el archivo json y los datos se coloquen entre [] como si fuera una lista y luego vienen las {}
    /*static public List<Administrador> obtenerAdmin(String correoAdmin) { //Recibe el atributo identificador de la clase
        try {
            Gson gson = new Gson();
            Object FilePath;
            JsonReader reader = new JsonReader(new FileReader("src//main//java//com//proyecto//demo//Json//administrador.json"));
            Administrador[] administradores = gson.fromJson(reader, Administrador[].class);
            List<Administrador> administradorLista = new ArrayList<>(Arrays.asList(administradores));
            List<Administrador> nuevaLista = new ArrayList<>();
            //Lo imprimo
            for (Administrador administrador : administradorLista) {
                if (administrador.getCorreo().equals(correoAdmin)) {
                    nuevaLista.add(administrador);
                    System.out.println("    Nombre del Admin: " + administrador.getNombre());
                    System.out.println("    Correo del Admin: " + administrador.getCorreo());
                    System.out.println("    Contraseña: " + administrador.getContrasena());
                    System.out.println("    Numero de Telefonico: " + administrador.getNumeroTelefonico());
                }
            }
            return nuevaLista;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/


    ////////////////////////////////////////////////////GUARDAR////////////////////////////////////////////////////

    //FUNCIONAL
    //PRIMERA FORMA DE GUARDAR UN ADMIN EN EL JSON
    //En esta forma es cuando es un solo objeto, no funciona si la estructura del archivo json es de lista
    public static void guardarAdministrador(Administrador administrador) throws IOException { //Se le pasa el objeto que deseo guardar
       // Validar si el archivo existe y está vacío
       File archivo = new File(ADMIN_PATH);
       if (archivo.exists() && archivo.length() > 0) {
           throw new IOException("El archivo JSON ya contiene datos, y solo puede tener un Administrador");
       }

       Gson gson = new Gson();
       String json = gson.toJson(administrador);

       // Sobrescribimos el archivo JSON existente
       FileWriter writer = new FileWriter(ADMIN_PATH);
       writer.write(json);
       writer.close();
   }


   //SEGUNDA FORMA DE GUARDAR UN ADMIN EN EL JSON
   // Para guardar un objeto en json (Original, es decir, con lista)
   /* static public void guardarAdministrador(Administrador administrador) { //Le paso el objeto que quiero guardar en el json
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader("src//main//java//com//proyecto//demo//Json//administrador.json"));
            Administrador administradores = gson.fromJson(reader, Administrador[].class);
            List<Administrador> administradorLista = new ArrayList<>(Arrays.asList(administradores));

            administradorLista.add(administrador);

            FileWriter fw = new FileWriter("src//main//java//com//proyecto//demo//Json//administrador.jsonn");
            StringWriter sw = new StringWriter();
            sw.write(gson.toJson(administradorLista));
            fw.write(sw.toString());
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    ////////////////////////////////////////////////////ELIMINAR///////////////////////////////////////////////////////


    //FUNCIONAL
    //PRIMERA FORMA DE ELIMINAR ADMIN
    //Para Eliminar un objeto admin del JSON
    public static void eliminarAdministrador() throws IOException {
        // Validar si el archivo existe y tiene contenido (Para poder eliminar el admin, debe tener informacion el archivo)
        File archivo = new File(ADMIN_PATH);
        if (!archivo.exists() || archivo.length() == 0) { //Si no lo encuentra o esta vacio
            throw new IOException("El archivo JSON no existe o está vacío.");
        }

        // Sobreescribir el archivo con una cadena vacía
        FileWriter writer = new FileWriter(archivo, false); // false para sobrescribir
        writer.write("");
        System.out.println("El archivo JSON se ha eliminado.");
        writer.close();
    }

    //FUNCIONAL
    //SEGUNDA FORMA DE ELIMINAR ADMIN
    //Para Eliminar un objeto especifico de la lista (ORIGINAL, es decir, cuando es una lista)
    /* public static void eliminarAdministrador(String correoAdmin) throws IOException {
        // Leer el JSON existente
        Gson gson = new Gson();
        List<Administrador> administradores = gson.fromJson(new FileReader("src//main//java//com//proyecto//demo//Json//administrador.json"), new TypeToken<List<Administrador>>() {
        }.getType());

        // Eliminar el producto
        List<Administrador> administradoresActualizados = new ArrayList<>();
        for (Administrador administrador : administradores) {
            if (!administrador.getCorreo().equalsIgnoreCase(correoAdmin)) {
                administradoresActualizados.add(administrador);
            }
        }

        // Escribir el JSON actualizado
        try (FileWriter writer = new FileWriter("src//main//java//com//proyecto//demo//Json//administrador.json")) {
            gson.toJson(administradoresActualizados, writer);
        }


    }*/
    
}