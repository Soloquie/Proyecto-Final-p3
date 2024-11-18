package uniquindio.finalproject;

import uniquindio.finalproject.Model.BilleteraAplicacion;
import uniquindio.finalproject.Model.Categoria;
import uniquindio.finalproject.Model.Cuenta;
import uniquindio.finalproject.Model.Usuario;
import uniquindio.finalproject.controller.ModelFactoryController;
import uniquindio.finalproject.persistencia.Persistencia;
import uniquindio.finalproject.sockets.Server;

import java.io.IOException;

public class MainAplication {

    public static void main(String[] args) {
        ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();
        modelFactoryController.start();
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
        Persistencia persistencia = new Persistencia();
        try {
            BilleteraAplicacion billeteraAplicacion = persistencia.cargarBilleteraVirtualBinario();
            for(Usuario usuario : billeteraAplicacion.getUsuarios()){
                for(Cuenta cuenta : usuario.getCuentasAsociadas()){
                    System.out.println(cuenta.getNombreBanco());
                }
                for(Categoria categoria : usuario.getCategoriasAsociadas()){
                    System.out.println("Categorias: "+categoria.getIdCategoria());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Inicia la aplicación cliente (Interfaz gráfica)
        ClientApp.launch(ClientApp.class, args);
    }
}
