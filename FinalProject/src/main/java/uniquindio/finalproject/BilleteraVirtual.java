package uniquindio.finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BilleteraVirtual extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BilleteraVirtual.class.getResource("VistaLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Gestion Usuario ");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}