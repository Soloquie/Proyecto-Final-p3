package uniquindio.finalproject.sockets;

import uniquindio.finalproject.Model.Usuario;
import uniquindio.finalproject.Model.Administrador;
import uniquindio.finalproject.controller.ModelFactoryController;
import uniquindio.finalproject.mapping.dto.AdministradorDto;
import uniquindio.finalproject.mapping.dto.UsuarioDto;
import uniquindio.finalproject.mapping.mappers.BilleteraMapper;
import uniquindio.finalproject.request.LoginRequest;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

public class ClienteHandler extends Thread {

    private final Socket clientSocket;
    private final ModelFactoryController modelFactoryController;
    private final BilleteraMapper mapper = BilleteraMapper.INSTANCE;

    public ClienteHandler(Socket clientSocket, ModelFactoryController modelFactoryController) {
        this.clientSocket = clientSocket;
        this.modelFactoryController = modelFactoryController;
    }

    @Override
    public void run() {
        try (ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream())) {

            Object solicitud = inputStream.readObject();

            if (solicitud instanceof String) {
                // Si es un String, manejarlo como antes
                String tipoSolicitud = (String) solicitud;
                switch (tipoSolicitud) {
                    case "OBTENER_USUARIOS":
                        manejarObtenerUsuarios(outputStream);
                        break;

                    case "GUARDAR_USUARIO":
                        manejarGuardarUsuario(inputStream);
                        break;

                    case "ACTUALIZAR_USUARIO":
                        manejarActualizarUsuario(inputStream);
                        break;

                    case "ELIMINAR_USUARIO":
                        manejarEliminarUsuario(inputStream);
                        break;

                    default:
                        System.out.println("Solicitud no reconocida: " + solicitud);
                        break;
                }

            } else if (solicitud instanceof LoginRequest) {
                // Manejar solicitudes de login directamente
                LoginRequest loginRequest = (LoginRequest) solicitud;
                String username = loginRequest.getUsername();
                String password = loginRequest.getPassword();

                Object usuario = manejarLogin(username, password);
                if (usuario instanceof AdministradorDto administradorDto) {
                    outputStream.writeObject(administradorDto);
                } else if (usuario instanceof UsuarioDto usuarioDto) {
                    outputStream.writeObject(usuarioDto);
                } else {
                    outputStream.writeObject(null); // Credenciales inválidas
                }
            } else {
                System.out.println("Solicitud no reconocida");
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Object manejarLogin(String username, String password) {
        Optional<Usuario> usuarioOpt = modelFactoryController.obtenerUsuarios().stream()
                .filter(u -> u.getUsuarioID().equals(username) && u.getContraseña().equals(password))
                .findFirst();

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario instanceof Administrador) {
                return mapper.administradorToAdministradorDto((Administrador) usuario);
            } else {
                return mapper.usuarioToUsuarioDto(usuario);
            }
        }
        return null; // Credenciales inválidas
    }




    private void manejarObtenerUsuarios(ObjectOutputStream outputStream) throws IOException {
        // Obtener la lista de usuarios desde el controlador y enviarla al cliente
        List<Usuario> usuarios = modelFactoryController.obtenerUsuarios();
        outputStream.writeObject(usuarios);
    }

    private void manejarGuardarUsuario(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        // Leer el usuario enviado por el cliente y guardarlo
        Usuario usuario = (Usuario) inputStream.readObject();
        modelFactoryController.guardarUsuario(usuario);
    }

    private void manejarActualizarUsuario(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        // Leer el usuario enviado por el cliente y actualizarlo
        Usuario usuario = (Usuario) inputStream.readObject();
        modelFactoryController.actualizarUsuario(usuario);
    }

    private void manejarEliminarUsuario(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        // Leer el usuario enviado por el cliente y eliminarlo
        Usuario usuario = (Usuario) inputStream.readObject();
        modelFactoryController.eliminarUsuario(usuario);
    }
}
