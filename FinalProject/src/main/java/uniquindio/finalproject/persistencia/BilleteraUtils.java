package uniquindio.finalproject.persistencia;

import uniquindio.finalproject.Model.*;

import java.util.LinkedList;

public class BilleteraUtils {

    public static BilleteraAplicacion inicializarDatos() {
        Administrador administrador = new Administrador("admin", "admin123");
        BilleteraAplicacion billeteraVirtual = new BilleteraAplicacion();

        // Crear algunas cuentas
        Cuenta cuenta1 = new Cuenta("1", "BancoA", "123456", TipoCuenta.AHORRO);
        Cuenta cuenta2 = new Cuenta("2", "BancoB", "654321", TipoCuenta.CORRIENTE);
        billeteraVirtual.getCuentas().add(cuenta1);
        billeteraVirtual.getCuentas().add(cuenta2);


        // Crear algunas categorías
        Categoria categoria1 = new Categoria("1", "Alimentación", "Gastos en comida y bebidas");
        Categoria categoria2 = new Categoria("2", "Transporte", "Gastos en transporte público y gasolina");
        billeteraVirtual.getCategorias().add(categoria1);
        billeteraVirtual.getCategorias().add(categoria2);

        // Crear algunos presupuestos
        Presupuesto presupuesto1 = new Presupuesto("1", "Presupuesto Mensual", 1000.0, 200.0, categoria1);
        administrador.getPresupuestos().add(presupuesto1);

        // Crear algunos usuarios
        Usuario usuario1 = new Usuario("001", "Juan Pérez", "juan@gmail.com", "123456789", "Dirección 1", 5000.0, "123");
        Usuario usuario2 = new Usuario("002", "Ana Gómez", "ana@gmail.com", "987654321", "Dirección 2", 7500.0, "123");
        billeteraVirtual.agregarUsuario(usuario1);
        billeteraVirtual.agregarUsuario(usuario2);
        billeteraVirtual.agregarUsuario(administrador);

        System.out.println("Información de la billetera virtual creada");
        return billeteraVirtual;
    }
}
