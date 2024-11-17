package uniquindio.finalproject.controller;

import uniquindio.finalproject.Model.*;
import uniquindio.finalproject.mapping.dto.CuentaDto;
import uniquindio.finalproject.mapping.dto.UsuarioDto;
import uniquindio.finalproject.mapping.mappers.BilleteraMapper;
import uniquindio.finalproject.persistencia.BilleteraUtils;
import uniquindio.finalproject.persistencia.Persistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModelFactoryController {

    private BilleteraAplicacion billeteraVirtual;
    private final BilleteraMapper mapper = BilleteraMapper.INSTANCE;
    private final Persistencia persistencia = new Persistencia();



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

    public void actualizarUsuario(Usuario usuario) {
        guardarDatos();
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
        for(Usuario usuario : billeteraVirtual.getUsuarios()) {
            if(usuario.getUsuarioID().equals(usuarioID)) {
                return usuario.getCuentasAsociadas();
            }
        }
        System.out.println("No se han encontrado cuentas del usuario");
        return null;
    }

    public boolean eliminarCuenta(CuentaDto cuentaDto) {
        Usuario usuario = encontrarUsuarioPorId(cuentaDto.usuario().getUsuarioID());
        if (usuario != null) {
            Cuenta cuentaAEliminar = usuario.getCuentasAsociadas().stream()
                    .filter(cuenta -> cuenta.getIdCuenta().equals(cuentaDto.idCuenta()))
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



}
