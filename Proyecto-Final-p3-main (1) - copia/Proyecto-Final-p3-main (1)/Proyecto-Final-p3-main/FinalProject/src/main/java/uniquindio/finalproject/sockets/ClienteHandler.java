package uniquindio.finalproject.sockets;

import uniquindio.finalproject.Model.*;
import uniquindio.finalproject.controller.ModelFactoryController;
import uniquindio.finalproject.mapping.dto.*;
import uniquindio.finalproject.mapping.mappers.BilleteraMapper;
import uniquindio.finalproject.request.LoginRequest;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClienteHandler extends Thread {

    private final Socket clientSocket;
    private final ModelFactoryController modelFactoryController;
    private final BilleteraMapper mapper = BilleteraMapper.INSTANCE;

    public ClienteHandler(Socket clientSocket, ModelFactoryController modelFactoryController) {
        this.clientSocket = clientSocket;
        this.modelFactoryController = modelFactoryController;
    }

    @Override
    public void run() {
        try (ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream())) {

            Object solicitud = inputStream.readObject();

            if (solicitud instanceof String) {
                String tipoSolicitud = (String) solicitud;
                switch (tipoSolicitud) {
                    case "OBTENER_USUARIOS":
                        manejarObtenerUsuarios(outputStream);
                        break;
                    case "GUARDAR_USUARIO":
                        manejarGuardarUsuario(inputStream);
                        break;
                    case "ACTUALIZAR_USUARIO":
                        manejarActualizarUsuario(inputStream, outputStream);
                        break;
                    case "ELIMINAR_USUARIO":
                        manejarEliminarUsuario(inputStream);
                        break;
                    default:
                        System.out.println("Solicitud no reconocida: " + solicitud);
                        break;
                    case "AGREGAR_PRESUPUESTO":
                        manejarAgregarPresupuesto(inputStream, outputStream);
                        break;
                    case "AGREGAR_CUENTA":
                        manejarAgregarCuenta(inputStream, outputStream);
                        break;
                    case "OBTENER_CUENTAS_USUARIO":
                        manejarObtenerCuentasUsuario(inputStream, outputStream);
                        break;
                    case "ELIMINAR_CUENTA":
                        manejarEliminarCuenta(inputStream, outputStream);
                        break;
                    case "ACTUALIZAR_CUENTA":
                        manejarActualizarCuenta(inputStream, outputStream);
                        break;
                    case "CREAR_TRANSACCION":
                        manejarCrearTransaccion(inputStream, outputStream);
                        break;
                    case "OBTENER_TODAS_CUENTAS":
                        manejarObtenerTodasCuentas(outputStream);
                        break;
                    case "OBTENER_TRANSACCIONES_USUARIO":
                        manejarObtenerTransaccionesUsuario(inputStream, outputStream);
                        break;
                    case "OBTENER_CATEGORIAS":
                        manejarObtenerCategorias(inputStream, outputStream);
                        break;
                    case "GUARDAR_CATEGORIA":
                        manejarGuardarCategoria(inputStream);
                        break;
                    case "ACTUALIZAR_CATEGORIA":
                        manejarActualizarCategoria(inputStream);
                        break;
                    case "ELIMINAR_CATEGORIA":
                        manejarEliminarCategoria(inputStream);
                        break;
                    case "OBTENER_PRESUPUESTOS":
                        manejarObtenerPresupuestos(inputStream, outputStream);
                        break;
                    case "GUARDAR_PRESUPUESTO":
                        manejarGuardarPresupuesto(inputStream, outputStream);
                        break;
                    case "ACTUALIZAR_PRESUPUESTO":
                        manejarActualizarPresupuesto(inputStream, outputStream);
                        break;
                    case "ELIMINAR_PRESUPUESTO":
                        manejarEliminarPresupuesto(inputStream, outputStream);
                        break;
                }

            } else if (solicitud instanceof LoginRequest) {
                // Manejar solicitudes de login directamente
                LoginRequest loginRequest = (LoginRequest) solicitud;
                String username = loginRequest.getUsername();
                String password = loginRequest.getPassword();

                Object usuario = manejarLogin(username, password);
                if (usuario instanceof AdministradorDto administradorDto) {
                    outputStream.writeObject(administradorDto);
                } else if (usuario instanceof UsuarioDto usuarioDto) {
                    outputStream.writeObject(usuarioDto);
                } else {
                    outputStream.writeObject(null); // Credenciales inválidas
                }

            }
            else {
                System.out.println("Solicitud no reconocida");
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Object manejarLogin(String username, String password) {
        Optional<Usuario> usuarioOpt = modelFactoryController.obtenerUsuarios().stream()
                .filter(u -> u.getUsuarioID().equals(username) && u.getContraseña().equals(password))
                .findFirst();

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario instanceof Administrador) {
                System.out.println("Administrador encontrado: " + usuario.getUsuarioID());
                return mapper.administradorToAdministradorDto((Administrador) usuario);
            } else {
                System.out.println("Usuario encontrado: " + usuario.getUsuarioID());
                System.out.println("Saldo del usuario: "+ usuario.getSaldoTotal());
                return mapper.usuarioToUsuarioDto(usuario);
            }
        }
        System.out.println("Credenciales no válidas");
        return null;
    }





    private void manejarObtenerUsuarios(ObjectOutputStream outputStream) throws IOException {
        // Obtener la lista de usuarios desde el controlador y enviarla al cliente
        List<Usuario> usuarios = modelFactoryController.obtenerUsuarios();
        outputStream.writeObject(usuarios);
    }

    private void manejarGuardarUsuario(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        // Leer el usuario enviado por el cliente y guardarlo
        Usuario usuario = (Usuario) inputStream.readObject();
        modelFactoryController.guardarUsuario(usuario);
    }

    private void manejarActualizarUsuario(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        // Leer el usuario enviado por el cliente y actualizarlo
        Usuario usuario = (Usuario) inputStream.readObject();
        boolean response = modelFactoryController.actualizarUsuario(usuario);
        outputStream.writeObject(response);
    }

    private void manejarEliminarUsuario(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        // Leer el usuario enviado por el cliente y eliminarlo
        Usuario usuario = (Usuario) inputStream.readObject();
        modelFactoryController.eliminarUsuario(usuario);
    }

    private void manejarAgregarPresupuesto(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        // Leer el presupuesto enviado por el cliente
        Presupuesto presupuesto = (Presupuesto) inputStream.readObject();

        // Agregar el presupuesto a través del controlador
        boolean exito = modelFactoryController.agregarPresupuesto(presupuesto);

        // Enviar la respuesta al cliente
        outputStream.writeObject(exito);
    }

    private void manejarAgregarCuenta(ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        try {
            CuentaDto cuentaDto = (CuentaDto) inputStream.readObject();
            boolean exito = modelFactoryController.agregarCuenta(cuentaDto);
            outputStream.writeObject(exito);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void manejarObtenerCuentasUsuario(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        String usuarioID = (String) inputStream.readObject();
        List<Cuenta> cuentas = modelFactoryController.obtenerCuentasDeUsuario(usuarioID);
        List<CuentaDto> cuentasDto = cuentas.stream()
                .map(mapper::cuentaToCuentaDto) // Asegúrate de que mapper funcione correctamente
                .collect(Collectors.toList());
        outputStream.writeObject(cuentasDto);
    }


    private void manejarEliminarCuenta(ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        try {
            CuentaDto cuentaDto = (CuentaDto) inputStream.readObject();
            boolean exito = modelFactoryController.eliminarCuenta(cuentaDto);
            outputStream.writeObject(exito);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void manejarActualizarCuenta(ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        try {
            CuentaDto cuentaOriginal = (CuentaDto) inputStream.readObject();
            CuentaDto cuentaActualizada = (CuentaDto) inputStream.readObject();

            boolean exito = modelFactoryController.actualizarCuenta(cuentaOriginal, cuentaActualizada);
            outputStream.writeObject(exito);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void manejarCrearTransaccion(ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        try {
            UsuarioDto usuarioDto = (UsuarioDto) inputStream.readObject();
            String idTransaccion = (String) inputStream.readObject();
            LocalDate fecha = (LocalDate) inputStream.readObject();
            TipoTransaccion tipoTransaccion = (TipoTransaccion) inputStream.readObject();
            Double monto = (Double) inputStream.readObject();
            String descripcion = (String) inputStream.readObject();
            CuentaDto cuentaOrigen = (CuentaDto) inputStream.readObject();
            CuentaDto cuentaDestino = (CuentaDto) inputStream.readObject();

            boolean exito = modelFactoryController.crearTransaccion(usuarioDto, idTransaccion, fecha, tipoTransaccion, monto, descripcion, cuentaOrigen, cuentaDestino);
            outputStream.writeObject(exito);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void manejarObtenerTodasCuentas(ObjectOutputStream outputStream) throws IOException {
        List<Cuenta> cuentas = modelFactoryController.obtenerTodasCuentas();
        List<CuentaDto> cuentasDto = cuentas.stream().map(mapper::cuentaToCuentaDto).collect(Collectors.toList());
        outputStream.writeObject(cuentasDto);
    }

    private void manejarObtenerTransaccionesUsuario(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        String usuarioID = (String) inputStream.readObject();
        List<TransaccionDto> transacciones = modelFactoryController.obtenerTransaccionesDeUsuario(usuarioID);
        outputStream.writeObject(transacciones);
    }

    private void manejarObtenerCategorias(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        UsuarioDto usuario = (UsuarioDto) inputStream.readObject();
        List<CategoriaDto> categorias = modelFactoryController.obtenerCategorias(usuario);
        outputStream.writeObject(categorias);
    }


    private void manejarGuardarCategoria(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        UsuarioDto usuario = (UsuarioDto) inputStream.readObject();
        CategoriaDto categoriaDto = (CategoriaDto) inputStream.readObject();
        modelFactoryController.guardarCategoria(usuario, categoriaDto);
    }

    private void manejarActualizarCategoria(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        String idOriginal = (String) inputStream.readObject();
        CategoriaDto categoriaActualizada = (CategoriaDto) inputStream.readObject();
        UsuarioDto usuarioDto = (UsuarioDto) inputStream.readObject(); // Leer el UsuarioDto

        // Llamar a actualizarCategoria con los tres parámetros requeridos
        modelFactoryController.actualizarCategoria(idOriginal, categoriaActualizada, usuarioDto);
    }


    private void manejarEliminarCategoria(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        UsuarioDto usuario = (UsuarioDto) inputStream.readObject();
        String idCategoria = (String) inputStream.readObject();
        modelFactoryController.eliminarCategoria(usuario, idCategoria);
    }

    private void manejarObtenerPresupuestos(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        String usuarioID = (String) inputStream.readObject();
        List<PresupuestoDto> presupuestos = modelFactoryController.obtenerPresupuestosDeUsuario(usuarioID);
        outputStream.writeObject(presupuestos);
    }

    private void manejarGuardarPresupuesto(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        String usuarioID = (String) inputStream.readObject();
        PresupuestoDto presupuesto = (PresupuestoDto) inputStream.readObject();
        boolean exito = modelFactoryController.guardarPresupuesto(usuarioID, presupuesto);
        outputStream.writeObject(exito);
    }

    private void manejarActualizarPresupuesto(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        PresupuestoDto presupuesto = (PresupuestoDto) inputStream.readObject();
        String id = (String) inputStream.readObject();
        boolean exito = modelFactoryController.actualizarPresupuesto(presupuesto, id);
        outputStream.writeObject(exito);
    }

    private void manejarEliminarPresupuesto(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        String idPresupuesto = (String) inputStream.readObject();
        boolean exito = modelFactoryController.eliminarPresupuesto(idPresupuesto);
        outputStream.writeObject(exito);
    }



}
