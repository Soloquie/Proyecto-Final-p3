package uniquindio.finalproject.mapping.dto;

import uniquindio.finalproject.Model.Categoria;

import java.io.Serializable;

public record PresupuestoDto(
        String idPresupuesto,
        String nombrePresupuesto,
        Double montoTotal,
        Double montoGastado,
        Categoria categoria) implements Serializable {
    private static final long serialVersionUID = 1L;

    public String getIdPresupuesto() {
        return idPresupuesto;
    }

    public String getNombrePresupuesto() {
        return nombrePresupuesto;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public Double getMontoGastado() {
        return montoGastado;
    }

    public String getIdCategoria() {
        return categoria.getIdCategoria();
    }

    public String getNombreCategoria(){
        return categoria.getNombreCategoria();
    }

    public String getDescripcionCategoria(){
        return categoria.getDescripcionCategoria();
    }

    public Categoria getCategoria() {
        return categoria;
    }
}