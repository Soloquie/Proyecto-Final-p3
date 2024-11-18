package uniquindio.finalproject.controller;

import uniquindio.finalproject.mapping.dto.PresupuestoDto;
import uniquindio.finalproject.mapping.dto.UsuarioDto;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PresupuestosController {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;
    private final UsuarioDto usuario;

    public PresupuestosController(UsuarioDto usuario) {
        this.usuario = usuario;
    }

    public List<PresupuestoDto> obtenerPresupuestos() {
        List<PresupuestoDto> presupuestos = new ArrayList<>();
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            outputStream.writeObject("OBTENER_PRESUPUESTOS");
            outputStream.writeObject(usuario.usuarioID());

            Object response = inputStream.readObject();
            if (response instanceof List) {
                presupuestos = (List<PresupuestoDto>) response;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return presupuestos;
    }

    public void guardarPresupuesto(PresupuestoDto presupuesto) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            outputStream.writeObject("GUARDAR_PRESUPUESTO");
            outputStream.writeObject(usuario.usuarioID());
            outputStream.writeObject(presupuesto);
            boolean response = (boolean)inputStream.readObject();
            if(response){
                System.out.println("Presupuesto añadido correctamente");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void actualizarPresupuesto(PresupuestoDto presupuesto) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
            outputStream.writeObject("ACTUALIZAR_PRESUPUESTO");
            outputStream.writeObject(presupuesto);
            outputStream.writeObject(usuario.usuarioID());
            boolean response = (boolean)inputStream.readObject();
            if(response){
                System.out.println("Presupuesto actualizado correctamente");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarPresupuesto(String idPresupuesto) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
            boolean response = (boolean)inputStream.readObject();
            if(response){
                System.out.println("Presupuesto eliminado correctamente");
            }
            outputStream.writeObject("ELIMINAR_PRESUPUESTO");
            outputStream.writeObject(idPresupuesto);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
