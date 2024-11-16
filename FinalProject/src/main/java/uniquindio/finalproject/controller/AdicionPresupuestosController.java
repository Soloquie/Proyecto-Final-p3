package uniquindio.finalproject.controller;

import uniquindio.finalproject.Model.Categoria;
import uniquindio.finalproject.Model.Presupuesto;
import uniquindio.finalproject.Model.BilleteraAplicacion;
import uniquindio.finalproject.persistencia.Persistencia;

public class AdicionPresupuestosController {

    private final ModelFactoryController modelFactoryController;

    public AdicionPresupuestosController() {
        this.modelFactoryController = ModelFactoryController.getInstance();
    }

    public boolean añadirPresupuesto(String idPresupuesto, String nombrePresupuesto, String montoTotalStr, String montoGastadoStr, String nombreCategoria, String idCategoria, String descripcionCategoria) {
        if (idPresupuesto.isEmpty() || nombrePresupuesto.isEmpty() || montoTotalStr.isEmpty() || montoGastadoStr.isEmpty()) {
            return false;
        }

        double montoTotal;
        double montoGastado;
        try {
            montoTotal = Double.parseDouble(montoTotalStr);
            montoGastado = Double.parseDouble(montoGastadoStr);
        } catch (NumberFormatException e) {
            return false;
        }

        Categoria categoria = new Categoria(idCategoria, nombreCategoria, descripcionCategoria);
        Presupuesto nuevoPresupuesto = new Presupuesto(idPresupuesto, nombrePresupuesto, montoTotal, montoGastado, categoria);

        return modelFactoryController.agregarPresupuesto(nuevoPresupuesto);
    }
}
