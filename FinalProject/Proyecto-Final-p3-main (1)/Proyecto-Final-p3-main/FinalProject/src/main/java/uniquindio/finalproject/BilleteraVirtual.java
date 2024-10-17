package uniquindio.finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uniquindio.finalproject.global.SesionGlobal;
import uniquindio.finalproject.Model.Usuario;
import uniquindio.finalproject.persistencia.Serializador;
import uniquindio.finalproject.persistencia.Respaldo;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class BilleteraVirtual extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BilleteraVirtual.class.getResource("VistaLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Gestion Usuario ");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        try {
            String rutaBinario = "billetera.bin";  // Ruta del archivo binario
            String rutaXML = "billetera.xml";  // Ruta del archivo XML
            Serializador.serializarBinario(SesionGlobal.usuarios, rutaBinario);
            Serializador.serializarXML(SesionGlobal.usuarios, rutaXML); // Aquí se pasa la lista de usuarios
            LinkedList<Usuario> usuariosDeserializados = (LinkedList<Usuario>) Serializador.deserializarBinario(rutaBinario);
            mostrarUsuariosDeserializados(usuariosDeserializados);
            File archivoXML = new File(rutaXML);
            if (archivoXML.exists()) {
                String rutaRespaldoDir = "respaldo";
                Respaldo.respaldarArchivoXML(rutaXML, rutaRespaldoDir);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void mostrarUsuariosDeserializados(LinkedList<Usuario> usuariosDeserializados) {
        System.out.println("Usuarios deserializados:");
        for (Usuario usuario : usuariosDeserializados) {
            System.out.println("ID: " + usuario.getUsuarioID() + ", Nombre: " + usuario.getNombre() + ", Saldo: " + usuario.getSaldoTotal());
        }
    }

    public static void main(String[] args) {
        launch();  // Lanza la interfaz gráfica
    }
}
