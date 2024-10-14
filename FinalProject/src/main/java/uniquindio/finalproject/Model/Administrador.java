package uniquindio.finalproject.Model;

import uniquindio.finalproject.Interfaces.GestionUsuarioInterface;
import uniquindio.finalproject.Interfaces.GestionCuentasInterface;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Administrador extends Usuario implements GestionUsuarioInterface ,GestionCuentasInterface, Serializable {
    private LinkedList<Usuario> usuarios;
    private LinkedList<Presupuesto> presupuestos;
    private LinkedList<Cuenta> cuentas;
    private LinkedList<Transaccion> transacciones;
    private LinkedList<Categoria> categorias;

    public void setUsuarios(LinkedList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public LinkedList<Presupuesto> getPresupuestos() {
        return presupuestos;
    }

    @Override
    public void setPresupuestos(LinkedList<Presupuesto> presupuestos) {
        this.presupuestos = presupuestos;
    }

    public LinkedList<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(LinkedList<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }


    @Override
    public void setTransacciones(LinkedList<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }

    public LinkedList<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(LinkedList<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Administrador(String usuarioID, String nombre, String correo, String numeroTelefono, String direccion, Double saldoTotal, String contraseña) {
        super(usuarioID, nombre, correo, numeroTelefono, direccion, saldoTotal, contraseña);
        this.usuarios = new LinkedList<>();
        this.presupuestos = new LinkedList<>();
        this.cuentas = new LinkedList<>();
        this.transacciones = new LinkedList<>();
        this.categorias= new LinkedList<>();
    }



    @Override
    public void crearUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public boolean actualizarUsuario(Usuario usuario, Usuario usuarioActualizado) {
        int index = usuarios.indexOf(usuario);
        if (index != -1) {
            usuarios.set(index, usuarioActualizado);
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminarUsuario(Usuario usuario) {
        return usuarios.remove(usuario);
    }

    public Usuario buscarUsuario(String idUsuario) {
        for (Usuario usuario : usuarios) {
            if(usuario.getUsuarioID().equals(idUsuario)){
                return usuario;
            }
        }
        return null;
    }

    public LinkedList<Usuario>getUsuarios() {
        return usuarios;
    }

    @Override
    public void crearCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
    }

    @Override
    public boolean actualizarCuenta(Cuenta cuenta, Cuenta cuentaActualizada) {
        int index = cuentas.indexOf(cuenta);
        if (index != -1) {
            cuentas.set(index, cuentaActualizada);
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminarCuenta(Cuenta cuenta) {
        return cuentas.remove(cuenta);
    }

    public void crearTransaccion(Transaccion transaccion) {
        transacciones.add(transaccion);
    }

    public void crearCategoria(Categoria categoria) {
        categorias.add(categoria);
    }

    public void mostrarEstadisticas() {
        Map<String, Double> gastosPorCategoria = new HashMap<>();
        for (Transaccion transaccion : transacciones) {
            String categoria = transaccion.getCategoria().getNombreCategoria(); // Asumiendo que Transaccion tiene una categoría
            gastosPorCategoria.put(categoria, gastosPorCategoria.getOrDefault(categoria, 0.0) + transaccion.getMonto());
        }
        List<Map.Entry<String, Double>> gastosOrdenados = gastosPorCategoria.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .toList();

        System.out.println("Gastos más comunes:");
        for (Map.Entry<String, Double> entry : gastosOrdenados) {
            System.out.println(entry.getKey() + ": $" + entry.getValue());
        }

        Map<Usuario, Long> transaccionesPorUsuario = transacciones.stream()
                .collect(Collectors.groupingBy(Transaccion::getUsuario, Collectors.counting()));

        List<Map.Entry<Usuario, Long>> usuariosConMasTransacciones = transaccionesPorUsuario.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        System.out.println("\nUsuarios con más transacciones:");
        for (Map.Entry<Usuario, Long> entry : usuariosConMasTransacciones) {
            System.out.println(entry.getKey().getNombre() + ": " + entry.getValue() + " transacciones");
        }

        double saldoTotal = usuarios.stream().mapToDouble(Usuario::getSaldoTotal).sum();
        double saldoPromedio = saldoTotal / usuarios.size();

        System.out.println("\nSaldo promedio de los usuarios: $" + saldoPromedio);
    }
}
