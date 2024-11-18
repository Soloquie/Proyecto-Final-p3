package uniquindio.finalproject.view;

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
import uniquindio.finalproject.controller.TablaTransaccionesController;
import uniquindio.finalproject.mapping.dto.TransaccionDto;
import uniquindio.finalproject.mapping.dto.UsuarioDto;

import java.io.IOException;
import java.time.LocalDate;

public class TablaTransaccionesViewController {

    @FXML
    private TableColumn<TransaccionDto, String> colIdTransaccion;

    @FXML
    private TableColumn<TransaccionDto, LocalDate> colFecha;

    @FXML
    private TableColumn<TransaccionDto, String> colTipoTransaccion;

    @FXML
    private TableColumn<TransaccionDto, Double> colMonto;

    @FXML
    private TableColumn<TransaccionDto, String> colCuentaOrigen;

    @FXML
    private TableColumn<TransaccionDto, String> colCuentaDestino;

    @FXML
    private TableView<TransaccionDto> tablaTransaccionesUsuario;

    @FXML
    private Button btnBack;

    @FXML
    private TextField txtBuscar;

    private TablaTransaccionesController transaccionController;
    private ObservableList<TransaccionDto> listaTransacciones;

    @FXML
    public void initialize() {
        colIdTransaccion.setCellValueFactory(new PropertyValueFactory<>("idTransaccion"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colTipoTransaccion.setCellValueFactory(new PropertyValueFactory<>("tipoTransaccion"));
        colMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
        colCuentaOrigen.setCellValueFactory(new PropertyValueFactory<>("cuentaOrigen"));
        colCuentaDestino.setCellValueFactory(new PropertyValueFactory<>("cuentaDestino"));

        listaTransacciones = FXCollections.observableArrayList();
        tablaTransaccionesUsuario.setItems(listaTransacciones);
    }

    public void setUsuarioActual(UsuarioDto usuario) {
        this.transaccionController = new TablaTransaccionesController();
        this.transaccionController.setUsuarioActual(usuario);
        cargarTransacciones();
    }

    private void cargarTransacciones() {
        listaTransacciones.setAll(transaccionController.getListaTransacciones());
        tablaTransaccionesUsuario.refresh();
    }

    @FXML
    void ClickBack(ActionEvent event) {
        abrirVistaTransacciones("/uniquindio/finalproject/VistaGestionDeTransacciones.fxml", event, transaccionController.getUsuarioActual());
    }


    private void abrirVistaTransacciones(String vista, ActionEvent event, UsuarioDto usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(vista));
            Parent root = loader.load();
            GestionTransaccionViewController controller = loader.getController();
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
}
