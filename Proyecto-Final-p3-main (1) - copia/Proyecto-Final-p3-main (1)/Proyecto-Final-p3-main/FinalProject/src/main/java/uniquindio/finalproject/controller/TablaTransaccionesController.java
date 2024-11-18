package uniquindio.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uniquindio.finalproject.mapping.dto.TransaccionDto;
import uniquindio.finalproject.mapping.dto.UsuarioDto;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class TablaTransaccionesController {

    private UsuarioDto usuarioActual;
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;
    private ObservableList<TransaccionDto> listaTransacciones = FXCollections.observableArrayList();

    public void setUsuarioActual(UsuarioDto usuario) {
        this.usuarioActual = usuario;
        cargarTransaccionesUsuario();
    }

    public ObservableList<TransaccionDto> getListaTransacciones() {
        return listaTransacciones;
    }

    private void cargarTransaccionesUsuario() {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            outputStream.writeObject("OBTENER_TRANSACCIONES_USUARIO");
            outputStream.writeObject(usuarioActual.usuarioID());

            Object response = inputStream.readObject();
            if (response instanceof List) {
                List<TransaccionDto> transacciones = (List<TransaccionDto>) response;
                listaTransacciones.setAll(transacciones);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public UsuarioDto getUsuarioActual() {
        return usuarioActual;
    }
}
