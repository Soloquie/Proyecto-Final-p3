package uniquindio.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import uniquindio.finalproject.Model.*;
import uniquindio.finalproject.global.SesionGlobal;

import java.io.IOException;
import java.time.LocalDate;

public class TablaTransaccionesController {

    @FXML
    public TableColumn <Transaccion, Usuario> colUsuario;

    @FXML
    private TableColumn<Transaccion, String> colIdTransaccion;

    @FXML
    private TableColumn<Transaccion, LocalDate> colFecha;

    @FXML
    private TableColumn<Transaccion, TipoTransaccion> colTipoTransaccion;

    @FXML
    private TableColumn<Transaccion, Double> colMonto;

    @FXML
    private TableColumn<Transaccion, Cuenta> colCuentaOrigen;

    @FXML
    private TableColumn<Transaccion, Cuenta> colCuentaDestino;

    @FXML
    private TableColumn<Transaccion, Categoria> colCategoria;

    @FXML
    private TableView<Transaccion> tablaTransaccionesUsuario;

    @FXML
    private Button btnBack;

    @FXML
    private TextField txtBuscar;

    private ObservableList<Transaccion> listaTransacciones;

    @FXML
    public void initialize() {
        colIdTransaccion.setCellValueFactory(new PropertyValueFactory<>("idTransaccion"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colTipoTransaccion.setCellValueFactory(new PropertyValueFactory<>("tipoTransaccion"));
        colMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
        colCuentaOrigen.setCellValueFactory(new PropertyValueFactory<>("cuentaOrigen"));
        colCuentaDestino.setCellValueFactory(new PropertyValueFactory<>("cuentaDestino"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        listaTransacciones = FXCollections.observableArrayList(SesionGlobal.usuarioActual.getTransaccionesAsociadas());
        tablaTransaccionesUsuario.setItems(listaTransacciones);
    }

    public void actualizarTabla() {
        listaTransacciones.setAll(SesionGlobal.usuarioActual.getTransaccionesAsociadas());
        tablaTransaccionesUsuario.refresh();
    }

    @FXML
    void ClickBack(ActionEvent event) {
        abrirVista("/uniquindio/finalproject/VistaGestionDeTransacciones.fxml", event);
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
}
