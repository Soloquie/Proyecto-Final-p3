package uniquindio.finalproject.controller;

import uniquindio.finalproject.Model.*;
import uniquindio.finalproject.mapping.dto.UsuarioDto;
import uniquindio.finalproject.mapping.mappers.BilleteraMapper;
import uniquindio.finalproject.persistencia.BilleteraUtils;
import uniquindio.finalproject.persistencia.Persistencia;

import java.io.IOException;
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

    private void guardarDatos() {
        try {
            persistencia.guardarBilleteraVirtualBinario(billeteraVirtual);
            persistencia.guardarBilleteraVirtualXML(billeteraVirtual);
        } catch (Exception e) {
            e.printStackTrace();
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

    public boolean agregarCuenta(Cuenta cuenta) {
        billeteraVirtual.agregarCuenta(cuenta);
        try {
            persistencia.guardarBilleteraVirtualBinario(billeteraVirtual);
            persistencia.guardarBilleteraVirtualXML(billeteraVirtual);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
