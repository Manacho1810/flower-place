package com.proyecto.demo.ManejadorJSON;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.proyecto.demo.Model.Cliente;
import com.proyecto.demo.Model.Design;
import com.proyecto.demo.Model.Pedido;


public class ClienteJson extends Cliente {
        private static final String BASE =
            System.getProperty("user.dir")
            + File.separator + "src" + File.separator + "main" + File.separator + "java"
            + File.separator + "com" + File.separator + "proyecto"
            + File.separator + "demo" + File.separator + "Json";

    private static final String CLIENTE_PATH =
            BASE + File.separator + "cliente.json";

    public ClienteJson(String correo, String contrasena, String nombre, String numeroTelefonico, ArrayList<Design> designs, ArrayList<Pedido> pedidos) {
        super(correo, contrasena, nombre, numeroTelefonico, designs, pedidos);
    }

    //PARA MANEJO DE ARCHIVOS
    //Para extraer informacion del Json
    static public List<Cliente> obtenerClientes(String correoCliente) { //Recibe el atributo identificador de la clase
        try {
            Gson gson = new Gson();
            Object FilePath;
            JsonReader reader = new JsonReader(new FileReader(CLIENTE_PATH));
            Cliente[] clientes = gson.fromJson(reader, Cliente[].class);
            List<Cliente> clienteLista = new ArrayList<>(Arrays.asList(clientes));
            List<Cliente> nuevaLista = new ArrayList<>();

            for (Cliente cliente : clienteLista) {
                if (cliente.getCorreo().equals(correoCliente)) {
                    nuevaLista.add(cliente);
                }
            }
            return nuevaLista;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Para extraer la lista completa
    static public ArrayList<Cliente> obtenerClientesTotales() {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(CLIENTE_PATH));
            Cliente[] clientes = gson.fromJson(reader, Cliente[].class);
            
            if (clientes == null || clientes.length == 0) {
                return new ArrayList<>(); // Retorna un ArrayList vacío
            }
            ArrayList<Cliente> clientesLista = new ArrayList<>(Arrays.asList(clientes));

            return clientesLista;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    //Para guardar un objeto en la lista del objeto en json
    static public void guardarCliente(Cliente cliente) { //Le paso el objeto que quiero guardar en la lista del json
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(CLIENTE_PATH));
            Cliente[] clientes = gson.fromJson(reader, Cliente[].class);
            List<Cliente> clienteLista= new ArrayList<>(Arrays.asList(clientes));

            clienteLista.add(cliente);

            FileWriter fw = new FileWriter(CLIENTE_PATH);
            StringWriter sw = new StringWriter();
            sw.write(gson.toJson(clienteLista));
            fw.write(sw.toString());
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Para Eliminar un objeto especifico de la lista
    public static void eliminarCliente(String correoCliente) throws IOException {
        // Leer el JSON existente
        Gson gson = new Gson();
        List<Cliente> clientes = gson.fromJson(new FileReader(CLIENTE_PATH), new TypeToken<List<Cliente>>() {}.getType());

        // Eliminar el producto
        List<Cliente> clientesActualizados = new ArrayList<>();
        for (Cliente cliente : clientes) {
            if (!cliente.getCorreo().equalsIgnoreCase(correoCliente)) {
                clientesActualizados.add(cliente);
            }
        }

        // Escribir el JSON actualizado
        try (FileWriter writer = new FileWriter(CLIENTE_PATH)) {
            gson.toJson(clientesActualizados, writer);
        }
    }

}
