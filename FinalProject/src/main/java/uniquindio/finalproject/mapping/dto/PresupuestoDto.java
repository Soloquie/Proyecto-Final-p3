package uniquindio.finalproject.mapping.dto;

import uniquindio.finalproject.Model.Categoria;

import java.io.Serializable;

public record PresupuestoDto(
        String idPresupuesto,
        String nombre,
        Double montoTotal,
        Double montoGastado,
        Categoria categoria) implements Serializable {
    private static final long serialVersionUID = 1L;
}