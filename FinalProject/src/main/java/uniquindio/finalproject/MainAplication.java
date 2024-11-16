package uniquindio.finalproject;

import uniquindio.finalproject.sockets.Server;

import java.io.IOException;

public class MainAplication {

    public static void main(String[] args) {
        // Inicia el servidor en un hilo separado
        Thread serverThread = new Thread(() -> {
            try {
                Server server = new Server(12345);
                server.start(); // Método para iniciar el servidor
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.setDaemon(true); // Finaliza el servidor al cerrar la aplicación
        serverThread.start();

        // Inicia la aplicación cliente (Interfaz gráfica)
        ClientApp.launch(ClientApp.class, args);
    }
}
