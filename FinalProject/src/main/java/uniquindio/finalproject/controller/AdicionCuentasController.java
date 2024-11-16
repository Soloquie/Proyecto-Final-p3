package uniquindio.finalproject.controller;

import uniquindio.finalproject.Model.Cuenta;
import uniquindio.finalproject.Model.TipoCuenta;

public class AdicionCuentasController {

    private final ModelFactoryController modelFactoryController;

    public AdicionCuentasController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public boolean guardarCuenta(String idCuenta, String nombreBanco, String numeroCuenta, String tipoCuentaStr) {
        if (idCuenta.isEmpty() || nombreBanco.isEmpty() || numeroCuenta.isEmpty() || tipoCuentaStr.equals("Seleccione tipo de cuenta")) {
            return false;
        }

        try {
            TipoCuenta tipoCuenta = TipoCuenta.valueOf(tipoCuentaStr);
            Cuenta nuevaCuenta = new Cuenta(idCuenta, nombreBanco, numeroCuenta, tipoCuenta);
            return modelFactoryController.agregarCuenta(nuevaCuenta);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
