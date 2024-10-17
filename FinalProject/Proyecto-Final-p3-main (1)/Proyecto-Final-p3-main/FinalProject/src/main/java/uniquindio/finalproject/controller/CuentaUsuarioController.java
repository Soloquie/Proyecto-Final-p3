package uniquindio.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uniquindio.finalproject.Model.Usuario;
import uniquindio.finalproject.global.SesionGlobal;
import uniquindio.finalproject.persistencia.ArchivoUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CuentaUsuarioController implements Initializable {

    @FXML
    private Button btnCategoria;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnCuentasDeUsuario;

    @FXML
    private Button btnEnviarDinero;

    @FXML
    private Button btnPerfilDeUsuario;

    @FXML
    private Button btnPresupuestos;

    @FXML
    private Button btnTransaccionesDeUsuario;

    @FXML
    private TextField txtIDCuenta;

    @FXML
    private TextField txtMonto;

    @FXML
    private TextField txtNombreBanco;

    @FXML
    private TextField txtNumeroCuenta;

    @FXML
    void ClickEnviarDinero(ActionEvent event) {

    }
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Cargar datos del usuario actual
        Usuario usuarioActual = SesionGlobal.usuarioActual;
        if (usuarioActual != null) {
            txtIDCuenta.setText(usuarioActual.getUsuarioID());
            txtNombreBanco.setText(usuarioActual.getNombre());
        }
    }


    private void abrirVista(String vista, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(vista));
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

    @FXML
    public void clickCuentasUsuario(ActionEvent event) {
        ArchivoUtil.guardarRegistroLog("Usuario: "+SesionGlobal.usuarioActual.getNombre()+" Cuenta de Usuario Controlador", 1, "clickCuentasUsuario", ("C:\\td\\Persistencia\\Log\\log.txt"));
        abrirVista("/uniquindio/finalproject/VistaGestionDeCuentas.fxml", event);
    }

    @FXML
    public void clickTransaccionesUsuario(ActionEvent event) {
        ArchivoUtil.guardarRegistroLog("Usuario: "+SesionGlobal.usuarioActual.getNombre()+" Cuenta de Usuario Controlador", 1, "clickTransaccionesUsuario", ("C:\\td\\Persistencia\\Log\\log.txt"));
        abrirVista("/uniquindio/finalproject/VistaGestionDeTransacciones.fxml", event);
    }

    @FXML
    public void clickPerfil(ActionEvent event) {
        ArchivoUtil.guardarRegistroLog("Usuario: "+SesionGlobal.usuarioActual.getNombre()+" Cuenta de Usuario Controlador", 1, "clickEditarPerfil", ("C:\\td\\Persistencia\\Log\\log.txt"));
        abrirVista("/uniquindio/finalproject/VistaEditarPerfil.fxml", event);
    }

    @FXML
    public void clickCerrarSesion(ActionEvent event) {
        ArchivoUtil.guardarRegistroLog("Usuario: "+SesionGlobal.usuarioActual.getNombre()+" Cuenta de Usuario Controlador", 1, "clickCerrarSesion", ("C:\\td\\Persistencia\\Log\\log.txt"));
        abrirVista("/uniquindio/finalproject/VistaLogin.fxml", event);
    }

    @FXML
    public void clickCategoriasUsuario(ActionEvent event) {
        ArchivoUtil.guardarRegistroLog("Usuario: "+SesionGlobal.usuarioActual.getNombre()+" Cuenta de Usuario Controlador", 1, "clickCategoriasUsuario", ("C:\\td\\Persistencia\\Log\\log.txt"));
        abrirVista("/uniquindio/finalproject/VistaCategorias.fxml", event);
    }

    @FXML
    public void clickPresupuestosUsuario(ActionEvent event) {
        ArchivoUtil.guardarRegistroLog("Usuario: "+SesionGlobal.usuarioActual.getNombre()+" Cuenta de Usuario Controlador", 1, "clickPresupuestosUsuario", ("C:\\td\\Persistencia\\Log\\log.txt"));
        abrirVista("/uniquindio/finalproject/VistaPresupuestos.fxml", event);
    }

}
