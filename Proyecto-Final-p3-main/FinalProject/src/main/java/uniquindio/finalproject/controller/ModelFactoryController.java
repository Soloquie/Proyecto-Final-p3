package uniquindio.finalproject.controller;

import uniquindio.finalproject.Model.*;
import uniquindio.finalproject.mapping.dto.*;
import uniquindio.finalproject.mapping.mappers.BilleteraMapper;
import uniquindio.finalproject.persistencia.BilleteraUtils;
import uniquindio.finalproject.persistencia.Persistencia;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModelFactoryController extends Thread {

    private BilleteraAplicacion billeteraVirtual;
    private final BilleteraMapper mapper = BilleteraMapper.INSTANCE;
    private final Persistencia persistencia = new Persistencia();

    public boolean crearTransaccion(UsuarioDto usuarioDto, String idTransaccion, LocalDate fecha, TipoTransaccion tipoTransaccion, Double monto, String descripcion, CuentaDto cuentaOrigenDto, CuentaDto cuentaDestinoDto) {
        // Convertir DTOs a objetos de entidad
        Usuario usuario = convertirDtoAUsuario(usuarioDto);
        Cuenta cuentaOrigen = convertirDtoACuenta(cuentaOrigenDto);
        Cuenta cuentaDestino = convertirDtoACuenta(cuentaDestinoDto);


        // Crear la nueva transacción
        Transaccion nuevaTransaccion = new Transaccion(usuario, idTransaccion, fecha, tipoTransaccion, monto, descripcion, cuentaOrigen, cuentaDestino, null);

        // Buscar al usuario en la lista de usuarios
        Usuario usuarioExistente = billeteraVirtual.getUsuarios().stream()
                .filter(u -> u.getUsuarioID().equals(usuario.getUsuarioID()))
                .findFirst()
                .orElse(null);

        if (usuarioExistente != null) {
            // Intentar añadir la transacción al usuario
                usuarioExistente.añadirTransaccion(nuevaTransaccion);
                // Si la transacción se agrega correctamente, guardar los datos
               System.out.println("Saldo del usuario actual: "+usuarioExistente.getSaldoTotal());
                return guardarDatos();

        } else {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }
    }


    private Usuario convertirDtoAUsuario(UsuarioDto usuarioDto) {
        return new Usuario(usuarioDto.usuarioID(), usuarioDto.nombre(), usuarioDto.correo(), usuarioDto.numeroTelefono(), usuarioDto.direccion(), usuarioDto.saldoTotal(), usuarioDto.contraseña());
    }

    private Cuenta convertirDtoACuenta(CuentaDto cuentaDto) {
        return new Cuenta(cuentaDto.idCuenta(), cuentaDto.nombreBanco(), cuentaDto.numeroCuenta(), cuentaDto.tipoCuenta(), convertirDtoAUsuario(convertirUsuarioADto(cuentaDto.usuario())), cuentaDto.saldo());
    }

    private UsuarioDto convertirUsuarioADto(Usuario usuario) {
        return new UsuarioDto(usuario.getUsuarioID(), usuario.getNombre(), usuario.getCorreo(), usuario.getNumeroTelefono(), usuario.getDireccion(), usuario.getSaldoTotal(), usuario.getContraseña());
    }

    public List<Cuenta> obtenerTodasCuentas() {
        List<Cuenta> todasLasCuentas = new ArrayList<>();
        for (Usuario usuario : billeteraVirtual.getUsuarios()) {
            todasLasCuentas.addAll(usuario.getCuentasAsociadas());
        }
        return todasLasCuentas;
    }


    private static class SingletonHolder {
        private static final ModelFactoryController INSTANCE = new ModelFactoryController();
    }

    public static ModelFactoryController getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private ModelFactoryController() {
        cargarDatos();
    }

    public void cargarDatos() {
        try {
            billeteraVirtual = persistencia.cargarBilleteraVirtualBinario();
            if (billeteraVirtual == null) {
                billeteraVirtual = new BilleteraAplicacion();
                billeteraVirtual.inicializarDatosBase();
                try {
                    persistencia.guardarBilleteraVirtualBinario(billeteraVirtual);
                    persistencia.guardarBilleteraVirtualXML(billeteraVirtual);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object validarCredenciales(String username, String password) {
        Optional<Usuario> usuarioOpt = billeteraVirtual.getUsuarios().stream()
                .filter(u -> u.getUsuarioID().equals(username) && u.getContraseña().equals(password))
                .findFirst();

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario instanceof Administrador) {
                return mapper.administradorToAdministradorDto((Administrador) usuario);
            } else {
                return mapper.usuarioToUsuarioDto(usuario);
            }
        }
        return null;
    }

    public List<Usuario> obtenerUsuarios() {
        return billeteraVirtual.getUsuarios();
    }

    public void guardarUsuario(Usuario usuario) {
        billeteraVirtual.agregarUsuario(usuario);
        guardarDatos();
    }

    public boolean actualizarUsuario(Usuario usuarioDtoActualizado) {
        Usuario usuario = encontrarUsuarioPorId(usuarioDtoActualizado.getUsuarioID());

        if (usuario != null) {
            // Actualizamos los datos del usuario
            usuario.setUsuarioID(usuarioDtoActualizado.getUsuarioID());
            usuario.setNombre(usuarioDtoActualizado.getNombre());
            usuario.setCorreo(usuarioDtoActualizado.getCorreo());
            usuario.setDireccion(usuarioDtoActualizado.getDireccion());
            usuario.setNumeroTelefono(usuarioDtoActualizado.getNumeroTelefono());
            usuario.setContraseña(usuarioDtoActualizado.getContraseña());

            boolean guardadoExitoso = guardarDatos();
            if (guardadoExitoso) {
                System.out.println("Usuario actualizado y datos guardados correctamente.");
                return true;
            } else {
                System.out.println("Error al guardar los datos en persistencia.");
            }
        } else {
            System.out.println("No se ha encontrado el usuario con ID: " + usuarioDtoActualizado.getUsuarioID());
        }
        return false;
    }



    public void eliminarUsuario(Usuario usuario) {
        billeteraVirtual.getUsuarios().remove(usuario);
        guardarDatos();
    }

    private boolean guardarDatos() {
        try {
            persistencia.guardarBilleteraVirtualBinario(billeteraVirtual);
            persistencia.guardarBilleteraVirtualXML(billeteraVirtual);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean agregarPresupuesto(Presupuesto presupuesto) {
        billeteraVirtual.añadirPresupuesto(presupuesto);
        try {
            persistencia.guardarBilleteraVirtualBinario(billeteraVirtual);
            persistencia.guardarBilleteraVirtualXML(billeteraVirtual);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean agregarCuenta(CuentaDto cuentaDto) {
        Cuenta cuenta = new Cuenta(
                cuentaDto.idCuenta(),
                cuentaDto.nombreBanco(),
                cuentaDto.numeroCuenta(),
                cuentaDto.tipoCuenta(),
                cuentaDto.usuario(),
                cuentaDto.saldo()
        );

        Usuario usuario = encontrarUsuarioPorId(cuentaDto.usuario().getUsuarioID());
        if (usuario != null) {
            usuario.añadirCuenta(cuenta);
            return guardarDatos();
        }
        return false;
    }



    public List<Cuenta> obtenerCuentasDeUsuario(String usuarioID) {
        for (Usuario usuario : billeteraVirtual.getUsuarios()) {
            if (usuario.getUsuarioID().equals(usuarioID)) {
                return usuario.getCuentasAsociadas();
            }
        }
        System.out.println("No se han encontrado cuentas del usuario");
        return new ArrayList<>(); // Devolver lista vacía en lugar de null
    }


    public boolean eliminarCuenta(CuentaDto cuentaDto) {
        Cuenta cuenta = mapper.cuentaDtoTOCuenta(cuentaDto);
        Usuario usuario = encontrarUsuarioPorId(cuentaDto.usuario().getUsuarioID());
        if (usuario != null) {
            Cuenta cuentaAEliminar = usuario.getCuentasAsociadas().stream()
                    .filter(cuenta1 -> cuenta1.getIdCuenta().equals(cuenta.getIdCuenta()))
                    .findFirst()
                    .orElse(null);

            if (cuentaAEliminar != null) {
                usuario.getCuentasAsociadas().remove(cuentaAEliminar);
                return guardarDatos();
            }
        }
        return false;
    }

    private Usuario encontrarUsuarioPorId(String usuarioID) {
        return billeteraVirtual.getUsuarios().stream()
                .filter(usuario -> usuario.getUsuarioID().equals(usuarioID))
                .findFirst()
                .orElse(null);
    }

    public boolean actualizarCuenta(CuentaDto cuentaOriginal, CuentaDto cuentaActualizada) {
        Usuario usuario = encontrarUsuarioPorId(cuentaOriginal.usuario().getUsuarioID());
        if (usuario != null) {
            // Encontrar la cuenta original en el usuario
            Cuenta cuentaAActualizar = usuario.getCuentasAsociadas().stream()
                    .filter(cuenta -> cuenta.getIdCuenta().equals(cuentaOriginal.idCuenta()))
                    .findFirst()
                    .orElse(null);

            if (cuentaAActualizar != null) {
                // Reemplazar los datos de la cuenta original con los de cuentaActualizada
                cuentaAActualizar.setNombreBanco(cuentaActualizada.nombreBanco());
                cuentaAActualizar.setNumeroCuenta(cuentaActualizada.numeroCuenta());
                cuentaAActualizar.setTipoCuenta(cuentaActualizada.tipoCuenta());
                cuentaAActualizar.setSaldo(cuentaActualizada.saldo());
                return guardarDatos();
            }
        }
        return false;
    }

    public List<TransaccionDto> obtenerTransaccionesDeUsuario(String usuarioID) {
        Usuario usuario = encontrarUsuarioPorId(usuarioID);
        if (usuario != null) {
            return usuario.getTransaccionesAsociadas().stream()
                    .map(mapper::transaccionToTransaccionDto)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }



    public List<CategoriaDto> obtenerCategorias(UsuarioDto usuarioDto) {
        Usuario usuario = billeteraVirtual.getUsuarios().stream()
                .filter(u -> u.getUsuarioID().equals(usuarioDto.usuarioID()))
                .findFirst()
                .orElse(null);

        if (usuario != null) {
            return usuario.getCategoriasAsociadas().stream()
                    .map(mapper::categoriaToCategoriaDto)
                    .collect(Collectors.toList());
        } else {
            System.out.println("Usuario no encontrado.");
            return new ArrayList<>(); // Devolver una lista vacía si no se encuentra el usuario
        }
    }


    public void guardarCategoria(UsuarioDto usuarioDto, CategoriaDto categoriaDto) {
        Categoria categoria = mapper.categoriaDtoToCategoria(categoriaDto);

        Usuario usuario = billeteraVirtual.getUsuarios().stream()
                .filter(u -> u.getUsuarioID().equals(usuarioDto.usuarioID()))
                .findFirst()
                .orElse(null);

        if (usuario != null) {
            usuario.añadirCategoria(categoria);
            guardarDatos();
        } else {
            System.out.println("Usuario no encontrado para guardar la categoría.");
        }
    }



    public boolean actualizarCategoria(String idOriginal, CategoriaDto categoriaActualizadaDto, UsuarioDto usuarioDto) {
        Categoria categoriaActualizada = mapper.categoriaDtoToCategoria(categoriaActualizadaDto);

        Usuario usuario = convertirDtoAUsuario(usuarioDto);
        List<Categoria> categorias = usuario.getCategoriasAsociadas();

        Categoria categoria = categorias.stream()
                .filter(c -> c.getIdCategoria().equals(idOriginal))
                .findFirst()
                .orElse(null);

        if (categoria != null) {
            categoria.setIdCategoria(categoriaActualizada.getIdCategoria());
            categoria.setNombre(categoriaActualizada.getNombreCategoria());
            categoria.setDescripcion(categoriaActualizada.getDescripcionCategoria());
            return guardarDatos();
        }
        return false;
    }

    public void eliminarCategoria(UsuarioDto usuarioDto, String idCategoria) {
        Usuario usuario = convertirDtoAUsuario(usuarioDto);
        List<Categoria> categorias = usuario.getCategoriasAsociadas();

        categorias.removeIf(c -> c.getIdCategoria().equals(idCategoria));

        // Guardar cambios en persistencia
        guardarDatos();
    }

    public List<PresupuestoDto> obtenerPresupuestosDeUsuario(String usuarioID) {
        Optional<Usuario> usuario = Optional.ofNullable(encontrarUsuarioPorId(usuarioID));
        return usuario
                .map(u -> u.getPresupuestos().stream()
                        .map(mapper::presupuestoToPresupuestoDto)
                        .collect(Collectors.toList()))
                .orElse(null);
    }

    public boolean guardarPresupuesto(String usuarioID, PresupuestoDto presupuestoDto) {
        Optional<Usuario> usuarioOpt = Optional.ofNullable(encontrarUsuarioPorId(usuarioID));
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Presupuesto presupuesto = mapper.presupuestoDtoToPresupuesto(presupuestoDto);
            usuario.añadirPresupuesto(presupuesto);
            return guardarDatos();
        }
        return false;
    }

    public boolean actualizarPresupuesto(PresupuestoDto presupuestoDto, String id) {
        Optional<Usuario> usuarioOpt = Optional.ofNullable(encontrarUsuarioPorId(id));
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Presupuesto presupuesto = usuario.getPresupuestos().stream()
                    .filter(p -> p.getIdPresupuesto().equals(presupuestoDto.idPresupuesto()))
                    .findFirst()
                    .orElse(null);

            if (presupuesto != null) {
                presupuesto.setNombre(presupuestoDto.nombrePresupuesto());
                presupuesto.setMontoTotal(presupuestoDto.getMontoTotal());
                presupuesto.setMontoGastado(presupuestoDto.getMontoGastado());
                presupuesto.setCategoria(presupuestoDto.getCategoria());
                return guardarDatos();
            }
        }
        return false;
    }

    public boolean eliminarPresupuesto(String idPresupuesto) {
        for (Usuario usuario : billeteraVirtual.getUsuarios()) {
            Presupuesto presupuesto = usuario.getPresupuestos().stream()
                    .filter(p -> p.getIdPresupuesto().equals(idPresupuesto))
                    .findFirst()
                    .orElse(null);

            if (presupuesto != null) {
                usuario.getPresupuestos().remove(presupuesto);
                return guardarDatos();
            }
        }
        return false;
    }

    public synchronized void guardarRespaldoAplicacion(){
        try {
            persistencia.guardarAplicacionRespaldoXML(billeteraVirtual);
            persistencia.guardarRegistroLog("Respaldo Guardado",1,"Se ha guardado un respaldo de la aplicacion");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (true){
            guardarRespaldoAplicacion();
            try {
                sleep(500000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
