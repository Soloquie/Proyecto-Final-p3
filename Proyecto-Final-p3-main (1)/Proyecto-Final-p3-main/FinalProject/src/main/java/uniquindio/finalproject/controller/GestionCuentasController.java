package uniquindio.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uniquindio.finalproject.Model.Usuario;
import uniquindio.finalproject.mapping.dto.CuentaDto;
import uniquindio.finalproject.mapping.dto.UsuarioDto;
import uniquindio.finalproject.Model.TipoCuenta;
import uniquindio.finalproject.view.CuentaUsuarioViewController;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GestionCuentasController {

    private final UsuarioDto usuario;
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public GestionCuentasController(UsuarioDto usuario) {
        this.usuario = usuario;
    }

    public void guardarCuenta(String idCuenta, String nombreBanco, String numeroCuenta, TipoCuenta tipoCuenta, Double saldo) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            // Crear CuentaDto con los datos
            Usuario usuario1 = convertirDtoAUsuario(usuario);
            CuentaDto nuevaCuenta = new CuentaDto(idCuenta, nombreBanco, numeroCuenta, tipoCuenta, usuario1, saldo);

            // Enviar solicitud y cuenta al servidor
            outputStream.writeObject("AGREGAR_CUENTA");
            outputStream.writeObject(nuevaCuenta);

            // Leer respuesta del servidor
            boolean response = (boolean) inputStream.readObject();
            if(response){
                System.out.println("Se ha agregado la cuenta correctamente");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Usuario convertirDtoAUsuario(UsuarioDto usuarioDto) {
        return new Usuario(usuarioDto.usuarioID(), usuarioDto.nombre(), usuarioDto.correo(), usuarioDto.numeroTelefono(),usuarioDto.direccion(),usuarioDto.saldoTotal(), usuarioDto.contraseña());

    }

    public void actualizarCuenta(String idCuenta, String nuevoId, String nombreBanco, String numeroCuenta, TipoCuenta tipoCuenta, Double saldo) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            // Crear CuentaDto con la información actualizada
            CuentaDto cuentaActualizada = new CuentaDto(nuevoId, nombreBanco, numeroCuenta, tipoCuenta, convertirDtoAUsuario(usuario), saldo);

            // Enviar solicitud y cuenta actualizada al servidor
            outputStream.writeObject("ACTUALIZAR_CUENTA");
            outputStream.writeObject(idCuenta); // ID de la cuenta a actualizar
            outputStream.writeObject(cuentaActualizada);

            // Leer respuesta del servidor
            String response = (String) inputStream.readObject();
            System.out.println("Respuesta del servidor: " + response);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void eliminarCuenta(String idCuenta) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            outputStream.writeObject("ELIMINAR_CUENTA");
            outputStream.writeObject(idCuenta);

            String response = (String) inputStream.readObject();
            System.out.println("Respuesta del servidor: " + response);

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
            outputStream.writeObject(usuario.usuarioID());

            Object response = inputStream.readObject();
            if (response instanceof List) {
                cuentas = (List<CuentaDto>) response;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cuentas;
    }

    public void volver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uniquindio/finalproject/VistaCuentaDeUsuario.fxml"));
            Parent root = loader.load();
            CuentaUsuarioViewController controller = loader.getController();
            controller.setUsuarioActual(usuario);
            // Configurar y mostrar la nueva ventana
            Stage newStage = new Stage();
            newStage.setTitle("Cuenta de Usuario");
            newStage.setScene(new Scene(root));
            newStage.show();

            // Cerrar la ventana actual
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
