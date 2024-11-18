package uniquindio.finalproject.sockets;

import uniquindio.finalproject.controller.ModelFactoryController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static Server instance; // Singleton
    private final ServerSocket serverSocket;
    private final ModelFactoryController modelFactoryController;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        modelFactoryController = ModelFactoryController.getInstance(); // Obtener instancia de ModelFactoryController
        modelFactoryController.cargarDatos();
    }

    public static Server getInstance(int port) throws IOException {
        if (instance == null) {
            instance = new Server(port);
        }
        return instance;
    }

    public void start() {
        System.out.println("Server started on port " + serverSocket.getLocalPort());
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                // Pasar el clientSocket y la instancia de modelFactoryController al ClienteHandler
                new Thread(new ClienteHandler(clientSocket, modelFactoryController)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
