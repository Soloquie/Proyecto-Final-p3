package uniquindio.finalproject.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uniquindio.finalproject.controller.GestionUsuarioController;
import uniquindio.finalproject.Model.Usuario;
import uniquindio.finalproject.persistencia.Persistencia;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GestionUsuarioViewController implements Initializable {

    private final ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    private final GestionUsuarioController usuarioController = new GestionUsuarioController();
    private Usuario usuarioSeleccionado;
    Persistencia persistencia = new Persistencia();

    @FXML
    private TextField txtId, txtNombre, txtCorreo, txtTelefono, txtDireccion, txtSaldo;
    @FXML
    private PasswordField txtContraseña;
    @FXML
    private TableView<Usuario> tableUsuarios;
    @FXML
    private TableColumn<Usuario, String> colId, colNombre, colCorreo, colTelefono, colDireccion, colSaldo;
    @FXML
    private Button btnAgregarCuentas, btnAgregarPresupuestos, btnMostrarEstadisticas, btnGuardar, btnActualizar, btnEliminar, btnLimpiar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Obtener los usuarios del controlador y agregarlos a la lista observable
        listaUsuarios.addAll(usuarioController.obtenerUsuarios());
        // Configurar las columnas de la tabla para mostrar las propiedades de Usuario
        colId.setCellValueFactory(new PropertyValueFactory<>("usuarioID"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("numeroTelefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldoTotal"));
        // Configurar la tabla para mostrar los usuarios
        tableUsuarios.setItems(listaUsuarios);

        // Listener para manejar la selección de usuario en la tabla
        tableUsuarios.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            usuarioSeleccionado = newValue;
            mostrarInformacionUsuario(usuarioSeleccionado);
        });
    }

    private void mostrarInformacionUsuario(Usuario usuario) {
        if (usuario != null) {
            txtId.setText(usuario.getUsuarioID());
            txtNombre.setText(usuario.getNombre());
            txtCorreo.setText(usuario.getCorreo());
            txtTelefono.setText(usuario.getNumeroTelefono());
            txtDireccion.setText(usuario.getDireccion());
            txtSaldo.setText(usuario.getSaldoTotal().toString());
        }
    }

    @FXML
    void ClickGuardar(ActionEvent event) {
        Usuario usuario = new Usuario(
                txtId.getText(),
                txtNombre.getText(),
                txtCorreo.getText(),
                txtTelefono.getText(),
                txtDireccion.getText(),
                Double.valueOf(txtSaldo.getText()),
                txtContraseña.getText()
        );
        usuarioController.guardarUsuario(usuario);
        listaUsuarios.add(usuario);
        persistencia.guardarRegistroLog("ClickGuardar", 1,"[Administrador] Se ha guardado un usuario");
        limpiarCampos();
    }

    @FXML
    void ClickActualizar(ActionEvent event) {
        usuarioSeleccionado.setNombre(txtNombre.getText());
        usuarioSeleccionado.setCorreo(txtCorreo.getText());
        usuarioSeleccionado.setNumeroTelefono(txtTelefono.getText());
        usuarioSeleccionado.setDireccion(txtDireccion.getText());
        usuarioSeleccionado.setSaldoTotal(Double.valueOf(txtSaldo.getText()));
        usuarioController.actualizarUsuario(usuarioSeleccionado);
        tableUsuarios.refresh();
        persistencia.guardarRegistroLog("ClickActualizar", 1,"[Administrador] Se ha actualizado a un usuario");
        limpiarCampos();
    }

    @FXML
    void ClickEliminar(ActionEvent event) {
        usuarioController.eliminarUsuario(usuarioSeleccionado);
        listaUsuarios.remove(usuarioSeleccionado);
        persistencia.guardarRegistroLog("ClickEliminar", 1,"[Administrador] Se ha eliminado a un usuario");
        limpiarCampos();
    }

    @FXML
    public void ClickLimpiar(ActionEvent event) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtId.clear();
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtDireccion.clear();
        txtSaldo.clear();
        txtContraseña.clear();
    }

    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    public void ClickAgregarCuentas(ActionEvent event) {
        persistencia.guardarRegistroLog("ClickAgregarCuentas", 1,"[Administrador] Se paso a la escena VistaAdicionDeCuantas");
        abrirVista("/uniquindio/finalproject/VistaAdicionDeCuentas.fxml", event, "Adición de Cuentas");
    }

    @FXML
    public void ClickPresupuestos(ActionEvent event) {
        persistencia.guardarRegistroLog("ClickPresupuestos", 1,"[Administrador] Se paso a la escena VistaAdicionPresupuestos");
        abrirVista("/uniquindio/finalproject/VistaAdicionDePresupuestos.fxml", event, "Adición de Presupuestos");
    }

    private void abrirVista(String vista, ActionEvent event, String titulo) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(vista));
            Stage newStage = new Stage();
            newStage.setTitle(titulo);
            newStage.setScene(new Scene(root));
            newStage.show();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickMostrarEstadisticas(ActionEvent event) {
        // Crear un Stage nuevo para mostrar las estadísticas
        Stage estadisticasStage = new Stage();
        estadisticasStage.setTitle("Estadísticas de Usuarios");
        persistencia.guardarRegistroLog("ClickMostrarEstadisticas", 1,"[Administrador] Se paso a la escena MostrarEstadisticas");


        // Crear un VBox para organizar los componentes
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        // Calcular estadísticas
        double saldoPromedio = listaUsuarios.stream()
                .mapToDouble(Usuario::getSaldoTotal)
                .average()
                .orElse(0);

        Map<String, Long> gastosComunes = listaUsuarios.stream()
                .collect(Collectors.groupingBy(
                        Usuario::getDireccion, // Cambiar 'direccion' por un atributo representativo de gasto
                        Collectors.counting()
                ));

        Usuario usuarioConMasTransacciones = listaUsuarios.stream()
                .max(Comparator.comparingInt(usuario -> usuario.getTransaccionesAsociadas().size())) // Suponiendo que "transacciones" es una lista en Usuario
                .orElse(null);

        // Crear elementos visuales para mostrar estadísticas
        Label lblSaldoPromedio = new Label("Saldo Promedio de Usuarios: " + String.format("%.2f", saldoPromedio));

        Label lblGastoMasComun = new Label("Gasto Más Común: " +
                (gastosComunes.isEmpty() ? "No hay datos" : gastosComunes.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse("N/A")));

        Label lblUsuarioMasTransacciones = new Label("Usuario con más transacciones: " +
                (usuarioConMasTransacciones == null ? "No hay datos" : usuarioConMasTransacciones.getNombre()));

        // Crear gráficos utilizando JavaFX Charts
        PieChart pieChartGastos = new PieChart();
        pieChartGastos.setTitle("Distribución de Gastos");
        gastosComunes.forEach((categoria, cantidad) ->
                pieChartGastos.getData().add(new PieChart.Data(categoria, cantidad))
        );

        BarChart<String, Number> barChartSaldos = new BarChart<>(
                new CategoryAxis(), new NumberAxis());
        barChartSaldos.setTitle("Saldos de Usuarios");
        barChartSaldos.getXAxis().setLabel("Usuarios");
        barChartSaldos.getYAxis().setLabel("Saldo");
        listaUsuarios.forEach(usuario -> {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(usuario.getNombre());
            series.getData().add(new XYChart.Data<>(usuario.getNombre(), usuario.getSaldoTotal()));
            barChartSaldos.getData().add(series);
        });

        // Agregar elementos al VBox
        vbox.getChildren().addAll(lblSaldoPromedio, lblGastoMasComun, lblUsuarioMasTransacciones, pieChartGastos, barChartSaldos);

        // Configurar la escena y mostrar la ventana
        Scene scene = new Scene(vbox, 800, 600);
        estadisticasStage.setScene(scene);
        estadisticasStage.show();
    }

    @FXML
    public void clickCerrarSesion(ActionEvent event) {
        persistencia.guardarRegistroLog("ClickCerrarSesion", 1,"[Administrador] Se paso a la escena Login");
        abrirVista("/uniquindio/finalproject/VistaLogin.fxml", event, "Adicion de Cuentas");
    }
}
