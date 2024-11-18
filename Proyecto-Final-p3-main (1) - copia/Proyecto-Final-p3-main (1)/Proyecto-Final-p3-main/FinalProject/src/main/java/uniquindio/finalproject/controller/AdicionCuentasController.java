package uniquindio.finalproject.controller;

import uniquindio.finalproject.Model.Cuenta;
import uniquindio.finalproject.Model.TipoCuenta;
import uniquindio.finalproject.Model.Usuario;
import uniquindio.finalproject.mapping.dto.CuentaDto;
import uniquindio.finalproject.mapping.dto.UsuarioDto;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class AdicionCuentasController {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public List<Usuario> obtenerUsuarios() {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            outputStream.writeObject("OBTENER_USUARIOS");
            Object respuesta = inputStream.readObject();

            if (respuesta instanceof List<?>) {
                return (List<Usuario>) respuesta;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<String> getTiposCuenta() {
        List<String> tipos = new ArrayList<>();
        for (TipoCuenta tipo : TipoCuenta.values()) {
            tipos.add(tipo.name());
        }
        return tipos;
    }

    public boolean guardarCuenta(String idCuenta, String nombreBanco, String numeroCuenta, String tipoCuentaStr, Usuario usuario, Double saldo) {
        if (idCuenta.isEmpty() || nombreBanco.isEmpty() || numeroCuenta.isEmpty() || tipoCuentaStr.equals("Seleccione tipo de cuenta")) {
            return false;
        }

        try {
            TipoCuenta tipoCuenta = TipoCuenta.valueOf(tipoCuentaStr);
            CuentaDto nuevaCuenta = new CuentaDto(idCuenta, nombreBanco, numeroCuenta, tipoCuenta, usuario, saldo);
            return enviarCuentaAlServidor(nuevaCuenta);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean enviarCuentaAlServidor(CuentaDto cuenta) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            outputStream.writeObject("AGREGAR_CUENTA");
            outputStream.writeObject(cuenta);

            Object respuesta = inputStream.readObject();
            return respuesta instanceof Boolean && (Boolean) respuesta;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
