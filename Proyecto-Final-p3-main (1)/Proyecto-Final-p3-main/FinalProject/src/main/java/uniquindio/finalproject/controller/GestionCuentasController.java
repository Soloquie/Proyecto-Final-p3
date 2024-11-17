package uniquindio.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uniquindio.finalproject.Model.TipoCuenta;
import uniquindio.finalproject.Model.Usuario;
import uniquindio.finalproject.mapping.dto.CuentaDto;
import uniquindio.finalproject.mapping.dto.UsuarioDto;
import uniquindio.finalproject.mapping.mappers.BilleteraMapper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GestionCuentasController {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;
    private UsuarioDto usuarioActual;

    public void setUsuarioActual(UsuarioDto usuario) {
        this.usuarioActual = usuario;
    }



    public boolean guardarCuenta(String idCuenta, String nombreBanco, String numeroCuenta, String tipoCuenta, double saldo) {
        // Convertir el tipoCuenta de String a TipoCuenta
        TipoCuenta tipoCuentaEnum = TipoCuenta.valueOf(tipoCuenta);

        // Convertir el UsuarioDto a Usuario usando el mapeador
        Usuario usuario = BilleteraMapper.INSTANCE.usuarioDtoToUsuario(usuarioActual);

        // Crear el CuentaDto con los tipos adecuados
        CuentaDto cuentaDto = new CuentaDto(idCuenta, nombreBanco, numeroCuenta, tipoCuentaEnum, usuario, saldo);

        // Enviar la cuenta al servidor
        return enviarCuentaAlServidor(cuentaDto, "AGREGAR_CUENTA");
    }

    public boolean eliminarCuenta(CuentaDto cuentaDto) {
        return enviarCuentaAlServidor(cuentaDto, "ELIMINAR_CUENTA");
    }

    public List<CuentaDto> obtenerCuentasUsuario() {
        List<CuentaDto> cuentas = new ArrayList<>();
        if (usuarioActual == null) {
            System.err.println("Error: usuarioActual es null. Asegúrate de que setUsuarioActual se haya llamado correctamente.");
            return cuentas;
        }

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            outputStream.writeObject("OBTENER_CUENTAS_USUARIO");
            outputStream.writeObject(usuarioActual.usuarioID());

            Object response = inputStream.readObject();
            if (response instanceof List<?>) {
                cuentas = (List<CuentaDto>) response;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cuentas;
    }


    private boolean enviarCuentaAlServidor(CuentaDto cuenta, String operacion) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            outputStream.writeObject(operacion);
            outputStream.writeObject(cuenta);

            Object response = inputStream.readObject();
            return response instanceof Boolean && (Boolean) response;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> obtenerTiposCuenta() {
        // Devolver una lista de tipos de cuenta como strings
        List<String> tipos = new ArrayList<>();
        for (TipoCuenta tipo : TipoCuenta.values()) {
            tipos.add(tipo.name());
        }
        return tipos;
    }

    public boolean actualizarCuenta(CuentaDto cuentaOriginal, CuentaDto cuentaActualizada) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            outputStream.writeObject("ACTUALIZAR_CUENTA");
            outputStream.writeObject(cuentaOriginal);
            outputStream.writeObject(cuentaActualizada);

            Object response = inputStream.readObject();
            return response instanceof Boolean && (Boolean) response;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
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

    public void volver(ActionEvent event) {
        abrirVista("/uniquindio/finalproject/VistaCuentaDeUsuario.fxml", event);
    }
}
