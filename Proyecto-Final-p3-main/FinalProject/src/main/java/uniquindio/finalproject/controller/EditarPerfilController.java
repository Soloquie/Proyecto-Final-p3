package uniquindio.finalproject.controller;

import uniquindio.finalproject.Model.Usuario;
import uniquindio.finalproject.mapping.dto.UsuarioDto;

import java.io.*;
import java.net.Socket;

public class EditarPerfilController {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;
    private UsuarioDto usuarioActual;

    public EditarPerfilController(UsuarioDto usuario) {
        setUsuarioActual(usuario);  // Inicializar usuario actual
    }

    public void setUsuarioActual(UsuarioDto usuario) {
        this.usuarioActual = usuario;
    }

    public UsuarioDto obtenerUsuarioActual() {
        return usuarioActual;
    }

    public boolean guardarCambios(UsuarioDto usuarioActualizado) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            // Convertir UsuarioDto a Usuario y enviar solicitud de actualización
            Usuario usuario = convertirDtoAUsuario(usuarioActualizado);
            outputStream.writeObject("ACTUALIZAR_USUARIO");
            outputStream.writeObject(usuario);

            // Leer respuesta del servidor
            Boolean resultado = (Boolean) inputStream.readObject();
            return resultado != null && resultado;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Usuario convertirDtoAUsuario(UsuarioDto usuarioDto) {
        return new Usuario(usuarioDto.usuarioID(), usuarioDto.nombre(), usuarioDto.correo(),
                usuarioDto.numeroTelefono(), usuarioDto.direccion(),
                usuarioDto.saldoTotal(), usuarioDto.contraseña());
    }
}
