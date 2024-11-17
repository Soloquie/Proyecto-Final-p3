package uniquindio.finalproject.controller;

import uniquindio.finalproject.Model.Usuario;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GestionUsuarioController {

    private static final String HOST = "localhost";
    private static final int PORT = 12345;

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            // Solicita los usuarios al servidor
            outputStream.writeObject("OBTENER_USUARIOS");

            // Leer la lista de usuarios del servidor
            usuarios = (List<Usuario>) inputStream.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public void guardarUsuario(Usuario usuario) {
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {

            // Solicitud para guardar usuario
            outputStream.writeObject("GUARDAR_USUARIO");
            outputStream.writeObject(usuario);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizarUsuario(Usuario usuario) {
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {

            // Solicitud para actualizar usuario
            outputStream.writeObject("ACTUALIZAR_USUARIO");
            outputStream.writeObject(usuario);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarUsuario(Usuario usuario) {
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {

            // Solicitud para eliminar usuario
            outputStream.writeObject("ELIMINAR_USUARIO");
            outputStream.writeObject(usuario);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
