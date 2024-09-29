package uniquindio.finalproject.Interfaces;

import uniquindio.finalproject.Model.Cuenta;

public interface GestionCuentasInterface {
    void crearCuenta(Cuenta cuenta);
    boolean actualizarCuenta(Cuenta cuenta, Cuenta cuentaActualizada);
    boolean eliminarCuenta(Cuenta cuenta);
}
