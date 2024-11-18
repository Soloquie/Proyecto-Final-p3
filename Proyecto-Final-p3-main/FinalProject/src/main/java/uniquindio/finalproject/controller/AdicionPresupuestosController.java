package uniquindio.finalproject.controller;

import uniquindio.finalproject.Model.Categoria;
import uniquindio.finalproject.Model.Presupuesto;

import java.io.*;
import java.net.Socket;

public class AdicionPresupuestosController {

    private static final String SERVER_HOST = "localhost"; // Dirección del servidor
    private static final int SERVER_PORT = 12345;         // Puerto del servidor

    public boolean añadirPresupuesto(String idPresupuesto, String nombrePresupuesto, String montoTotalStr, String montoGastadoStr, String nombreCategoria, String idCategoria, String descripcionCategoria) {
        if (idPresupuesto.isEmpty() || nombrePresupuesto.isEmpty() || montoTotalStr.isEmpty() || montoGastadoStr.isEmpty()) {
            return false;
        }

        double montoTotal;
        double montoGastado;
        try {
            montoTotal = Double.parseDouble(montoTotalStr);
            montoGastado = Double.parseDouble(montoGastadoStr);
        } catch (NumberFormatException e) {
            return false;
        }

        Categoria categoria = new Categoria(idCategoria, nombreCategoria, descripcionCategoria);
        Presupuesto nuevoPresupuesto = new Presupuesto(idPresupuesto, nombrePresupuesto, montoTotal, montoGastado, categoria);

        // Lógica para enviar el presupuesto al servidor
        return enviarPresupuestoAlServidor(nuevoPresupuesto);
    }

    private boolean enviarPresupuestoAlServidor(Presupuesto presupuesto) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            // Enviar solicitud al servidor
            outputStream.writeObject("AGREGAR_PRESUPUESTO"); // Tipo de solicitud
            outputStream.writeObject(presupuesto);          // Enviar objeto Presupuesto

            // Leer la respuesta del servidor
            Object respuesta = inputStream.readObject();
            if (respuesta instanceof Boolean) {
                return (Boolean) respuesta; // El servidor responde con éxito o fallo
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


}
