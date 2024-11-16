package uniquindio.finalproject.Model;

import java.io.Serializable;
import java.util.LinkedList;

public class BilleteraAplicacion implements Serializable {
    private static final long serialVersionUID = 1L;
    private LinkedList<Usuario> usuarios;
    private LinkedList<Cuenta> cuentas;
    private LinkedList<Transaccion> transacciones;
    private LinkedList<Categoria> categorias;
    private LinkedList<Presupuesto> presupuestos;

    public BilleteraAplicacion() {
        usuarios = new LinkedList<>();
        cuentas = new LinkedList<>();
        transacciones = new LinkedList<>();
        categorias = new LinkedList<>();
        presupuestos = new LinkedList<>();
    }

    public LinkedList<Usuario> getUsuarios() {
        return usuarios;
    }

    public LinkedList<Cuenta> getCuentas() {
        return cuentas;
    }

    public LinkedList<Transaccion> getTransacciones() {
        return transacciones;
    }

    public LinkedList<Categoria> getCategorias() {
        return categorias;
    }

    public void setUsuarios(LinkedList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void setCuentas(LinkedList<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public void setTransacciones(LinkedList<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }

    public void setCategorias(LinkedList<Categoria> categorias) {
        this.categorias = categorias;
    }

    public LinkedList<Presupuesto> getPresupuestos() {
        return presupuestos;
    }

    public void setPresupuestos(LinkedList<Presupuesto> presupuestos) {
        this.presupuestos = presupuestos;
    }

    // Método para inicializar los datos base de la aplicación
    public void inicializarDatosBase() {
        // Crear usuarios de ejemplo
        Usuario admin = new Administrador("admin", "admin123");
        usuarios.add(admin);

        Usuario usuario1 = new Usuario("001", "Juan Pérez", "juan@gmail.com", "123456789", "Dirección 1", 5000.0, "password1");
        usuarios.add(usuario1);

        // Crear cuentas de ejemplo
        Cuenta cuenta1 = new Cuenta("1", "BancoA", "123456", TipoCuenta.AHORRO);
        cuentas.add(cuenta1);

        Cuenta cuenta2 = new Cuenta("2", "BancoB", "654321", TipoCuenta.CORRIENTE);
        cuentas.add(cuenta2);

        // Crear categorías de ejemplo
        Categoria categoria1 = new Categoria("1", "Alimentación", "Gastos en comida y bebidas");
        categorias.add(categoria1);

        System.out.println("Datos base de la billetera virtual inicializados.");
    }

    public void agregarUsuario(Usuario usuario2) {
        usuarios.add(usuario2);
    }

    public void agregarCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
    }

    public void agregarTransaccion(Transaccion transaccion) {
        transacciones.add(transaccion);
    }

    public void agregarCategoria(Categoria categoria) {
        categorias.add(categoria);
    }

    @Override
    public String toString() {
        return "BilleteraAplicacion{" +
                "usuarios=" + usuarios +
                ", cuentas=" + cuentas +
                ", transacciones=" + transacciones +
                ", categorias=" + categorias +
                '}';
    }

    public void añadirPresupuesto(Presupuesto presupuesto) {
        if (this.presupuestos == null) {
            this.presupuestos = new LinkedList<>(); // Asegura la inicialización si es nulo
        }
        this.presupuestos.add(presupuesto);
    }

}
