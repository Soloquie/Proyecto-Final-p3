package uniquindio.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uniquindio.finalproject.Model.TipoTransaccion;
import uniquindio.finalproject.mapping.dto.CuentaDto;
import uniquindio.finalproject.mapping.dto.UsuarioDto;
import uniquindio.finalproject.view.CuentaUsuarioViewController;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestionTransaccionController {

    private final UsuarioDto usuarioActual;
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public UsuarioDto getUsuarioActual() {
        return usuarioActual;
    }

    public GestionTransaccionController(UsuarioDto usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public void enviarTransaccion(String idTransaccion, LocalDate fecha, TipoTransaccion tipoTransaccion, Double monto, String descripcion, CuentaDto cuentaOrigen, CuentaDto cuentaDestino) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            // Enviar solicitud de creación de transacción
            outputStream.writeObject("CREAR_TRANSACCION");

            // Crear y enviar datos de la transacción
            outputStream.writeObject(usuarioActual);
            outputStream.writeObject(idTransaccion);
            outputStream.writeObject(fecha);
            outputStream.writeObject(tipoTransaccion);
            outputStream.writeObject(monto);
            outputStream.writeObject(descripcion);
            outputStream.writeObject(cuentaOrigen);
            outputStream.writeObject(cuentaDestino);

            // Leer respuesta del servidor
            boolean response = (boolean) inputStream.readObject();
            if (response) {
                System.out.println("Transacción realizada exitosamente.");
            } else {
                System.out.println("Error al realizar la transacción.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<CuentaDto> obtenerCuentasUsuario() {
        List<CuentaDto> cuentas = new ArrayList<>();
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            outputStream.writeObject("OBTENER_CUENTAS_USUARIO");
            outputStream.writeObject(usuarioActual.usuarioID());

            Object response = inputStream.readObject();
            if (response instanceof List) {
                cuentas = (List<CuentaDto>) response;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cuentas;
    }

    public List<UsuarioDto> obtenerUsuarios() {
        List<UsuarioDto> usuarios = new ArrayList<>();
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            outputStream.writeObject("OBTENER_USUARIOS");

            Object response = inputStream.readObject();
            if (response instanceof List) {
                usuarios = (List<UsuarioDto>) response;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public List<CuentaDto> obtenerCuentasDeUsuario(String usuarioId) {
        List<CuentaDto> cuentas = new ArrayList<>();
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            outputStream.writeObject("OBTENER_CUENTAS_USUARIO");
            outputStream.writeObject(usuarioId);

            Object response = inputStream.readObject();
            if (response instanceof List) {
                cuentas = (List<CuentaDto>) response;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cuentas;
    }






    public List<CuentaDto> obtenerTodasCuentas() {
        List<CuentaDto> cuentas = new ArrayList<>();
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            outputStream.writeObject("OBTENER_TODAS_CUENTAS");

            Object response = inputStream.readObject();
            if (response instanceof List) {
                cuentas = (List<CuentaDto>) response;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cuentas;
    }
}
