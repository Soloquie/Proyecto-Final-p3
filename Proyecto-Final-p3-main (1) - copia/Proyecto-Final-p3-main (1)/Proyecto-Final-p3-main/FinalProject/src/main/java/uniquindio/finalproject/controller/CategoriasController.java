package uniquindio.finalproject.controller;

import uniquindio.finalproject.mapping.dto.CategoriaDto;
import uniquindio.finalproject.mapping.dto.UsuarioDto;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CategoriasController {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public List<CategoriaDto> obtenerCategorias(UsuarioDto usuario) {
        List<CategoriaDto> categorias = new ArrayList<>();
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            outputStream.writeObject("OBTENER_CATEGORIAS");
            outputStream.writeObject(usuario);

            Object response = inputStream.readObject();
            if (response instanceof List) {
                categorias = (List<CategoriaDto>) response;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return categorias;
    }

    public void guardarCategoria(UsuarioDto usuario, CategoriaDto categoriaDto) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {

            outputStream.writeObject("GUARDAR_CATEGORIA");
            outputStream.writeObject(usuario);
            outputStream.writeObject(categoriaDto);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actualizarCategoria(String idOriginal, CategoriaDto categoriaActualizada, UsuarioDto usuario) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {

            outputStream.writeObject("ACTUALIZAR_CATEGORIA");
            outputStream.writeObject(idOriginal);
            outputStream.writeObject(categoriaActualizada);
            outputStream.writeObject(usuario);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void eliminarCategoria(UsuarioDto usuario, String idCategoria) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {

            outputStream.writeObject("ELIMINAR_CATEGORIA");
            outputStream.writeObject(usuario);
            outputStream.writeObject(idCategoria);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
