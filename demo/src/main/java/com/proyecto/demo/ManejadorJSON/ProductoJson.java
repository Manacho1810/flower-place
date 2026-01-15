package com.proyecto.demo.ManejadorJSON;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.proyecto.demo.Model.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.reflect.TypeToken;


public class ProductoJson extends Producto {
        private static final String BASE =
            System.getProperty("user.dir")
            + File.separator + "src" + File.separator + "main" + File.separator + "java"
            + File.separator + "com" + File.separator + "proyecto"
            + File.separator + "demo" + File.separator + "Json";

    private static final String PRODUCTO_PATH =
            BASE + File.separator + "producto.json";

    public ProductoJson(String nombre, float precio, int cantidad) {
        super(nombre, precio, cantidad);
    }

    //FUNCIONES PARA ARCHIVOS

    //Para extraer informacion del Json buscando por un campo
    static public ArrayList<Producto> obtenerProductos (String nombreProducto) {
        try {
            Gson gson = new Gson();
            Object FilePath;
            JsonReader reader = new JsonReader(new FileReader(PRODUCTO_PATH));
            Producto[] productos = gson.fromJson(reader, Producto[].class);
            ArrayList<Producto> productoLista = new ArrayList<>(Arrays.asList(productos));
            ArrayList<Producto> nuevaLista = new ArrayList<Producto>();

            for (Producto producto : productoLista) {
                if (producto.getNombre().equals(nombreProducto)) {
                    nuevaLista.add(producto);
                }
            }

            /* for (Producto producto : nuevaLista) {
                System.out.println("Nombre: " + producto.getNombre());
                System.out.println("Precio: " + producto.getPrecio());
                System.out.println("Cantidad:" + producto.getCantidad());

            }*/
            return nuevaLista;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Para extraer la lista completa
    static public ArrayList<Producto> obtenerProductosTotales() {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(PRODUCTO_PATH));
            Producto[] productos = gson.fromJson(reader, Producto[].class);
            if (productos == null || productos.length == 0) {
                return new ArrayList<Producto>(); // Retorna un ArrayList vacío
            }
            ArrayList<Producto> productosLista = new ArrayList<>(Arrays.asList(productos));

            return productosLista;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    //Para guardar un objeto en la lista del objeto en json
    static public void guardarProducto(Producto producto) { //Le paso el objeto que quiero guardar en la lista del json
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(PRODUCTO_PATH));
            Producto[] productos = gson.fromJson(reader, Producto[].class);
            List<Producto> productoLista= new ArrayList<>(Arrays.asList(productos));

            productoLista.add(producto);

            FileWriter fw = new FileWriter(PRODUCTO_PATH);
            StringWriter sw = new StringWriter();
            sw.write(gson.toJson(productoLista));
            fw.write(sw.toString());
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Para Eliminar un objeto especifico de la lista
    public static void eliminarProducto(String nombreProducto) throws IOException {
        // Leer el JSON existente
        Gson gson = new Gson();
        List<Producto> productos = gson.fromJson(new FileReader(PRODUCTO_PATH), new TypeToken<List<Producto>>() {}.getType());

        // Eliminar el producto
        List<Producto> productosActualizados = new ArrayList<>();
        for (Producto producto : productos) {
            if (!producto.getNombre().equalsIgnoreCase(nombreProducto)) {
                productosActualizados.add(producto);
            }
        }

        // Escribir el JSON actualizado
        try (FileWriter writer = new FileWriter(PRODUCTO_PATH)) {
            gson.toJson(productosActualizados, writer);
        }
        catch (IOException e) {
        e.printStackTrace();
        }
    }

}



