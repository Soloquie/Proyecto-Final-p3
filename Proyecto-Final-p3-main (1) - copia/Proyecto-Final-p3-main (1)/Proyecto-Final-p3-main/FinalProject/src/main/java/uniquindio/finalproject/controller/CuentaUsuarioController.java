package uniquindio.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uniquindio.finalproject.mapping.dto.CuentaDto;
import uniquindio.finalproject.mapping.dto.UsuarioDto;
import uniquindio.finalproject.view.CuentaUsuarioViewController;
import uniquindio.finalproject.view.GestionCuentasViewController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CuentaUsuarioController {

    private final UsuarioDto usuario;
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public CuentaUsuarioController(UsuarioDto usuario) {
        this.usuario = usuario;
    }

    public UsuarioDto getUsuarioActual(){
        return usuario;
    }

    public List<CuentaDto> cargarCuentasUsuario(CuentaUsuarioViewController viewController) {
        viewController.mostrarSaldoUsuario(usuario.saldoTotal());
        return obtenerCuentasUsuario();
    }

    public List<CuentaDto> obtenerCuentasUsuario() {
        List<CuentaDto> cuentas = new ArrayList<>();
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            // Solicitar las cuentas del usuario
            outputStream.writeObject("OBTENER_CUENTAS_USUARIO");
            outputStream.writeObject(usuario.usuarioID());

            // Leer la respuesta del servidor
            Object response = inputStream.readObject();
            if (response instanceof List) {
                cuentas = (List<CuentaDto>) response;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cuentas;
    }


    private void abrirVista(String vista, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(vista));
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setTitle("Nueva Ventana");
            newStage.setScene(new Scene(root));
            newStage.show();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abrirVista1(String vista, ActionEvent event, UsuarioDto usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(vista));
            Parent root = loader.load();
            GestionCuentasViewController controller = loader.getController();
            controller.setUsuarioActual(usuario);  // Establece el usuario actual aquí

            // Ahora que el usuario se ha establecido, puedes cargar la tabla de cuentas
            Stage newStage = new Stage();
            newStage.setTitle("Nueva Ventana");
            newStage.setScene(new Scene(root));
            newStage.show();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void abrirVistaTransaccionesUsuario(ActionEvent event) {
        abrirVista("/uniquindio/finalproject/VistaGestionDeTransacciones.fxml", event);
    }

    public void abrirVistaEditarPerfil(ActionEvent event) {
        abrirVista("/uniquindio/finalproject/VistaEditarPerfil.fxml", event);
    }

    public void cerrarSesion(ActionEvent event) {
        abrirVista("/uniquindio/finalproject/VistaLogin.fxml", event);
    }

    public void abrirVistaCategoriasUsuario(ActionEvent event) {
        abrirVista("/uniquindio/finalproject/VistaCategorias.fxml", event);
    }

    public void abrirVistaPresupuestosUsuario(ActionEvent event) {
        abrirVista("/uniquindio/finalproject/VistaPresupuestos.fxml", event);
    }
}
