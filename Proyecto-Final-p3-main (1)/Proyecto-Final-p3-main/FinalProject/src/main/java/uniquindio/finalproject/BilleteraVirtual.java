package uniquindio.finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uniquindio.finalproject.Model.BilleteraAplicacion;
import uniquindio.finalproject.persistencia.Persistencia;
import uniquindio.finalproject.sockets.Server;

import java.io.IOException;

public class BilleteraVirtual extends Application {

    private static boolean ejecutarComoServidor = false; // Cambia a true si quieres iniciar el servidor

    @Override
    public void start(Stage stage) throws IOException {
        if (ejecutarComoServidor) {
            // No lanza la interfaz si es un servidor
            System.out.println("Modo servidor activado. Interfaz gráfica deshabilitada.");
            return;
        }

        // Cargar la interfaz gráfica como cliente
        FXMLLoader fxmlLoader = new FXMLLoader(BilleteraVirtual.class.getResource("VistaLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Gestión Usuario");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // Cargar la billetera desde el archivo binario
        Persistencia persistencia = new Persistencia();
        try {
            BilleteraAplicacion billetera = persistencia.cargarBilleteraVirtualBinario();
            System.out.println(billetera.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("servidor")) {
            ejecutarComoServidor = true; // Modo servidor activado
        }

        if (ejecutarComoServidor) {
            iniciarServidor();
        } else {
            launch(); // Lanza la interfaz gráfica
        }
    }

    /**
     * Método para iniciar el servidor.
     */
    private static void iniciarServidor() {
        System.out.println("Iniciando el servidor de la billetera virtual...");
        try {
            Server servidor = new Server(12345); // Puerto del servidor
            servidor.start(); // Inicia el servidor y espera conexiones
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
