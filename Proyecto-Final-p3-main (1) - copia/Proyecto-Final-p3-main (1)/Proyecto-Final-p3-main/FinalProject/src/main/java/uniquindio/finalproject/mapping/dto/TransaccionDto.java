package uniquindio.finalproject.mapping.dto;

import uniquindio.finalproject.Model.Categoria;
import uniquindio.finalproject.Model.Cuenta;
import uniquindio.finalproject.Model.TipoTransaccion;

import java.io.Serializable;
import java.time.LocalDate;

public record TransaccionDto (
        String idTransaccion,
        LocalDate fecha,
        TipoTransaccion tipoTransaccion,
        Double monto,
        String descripcion,
        Cuenta cuentaOrigen,
        Cuenta cuentaDestino,
        Categoria categoria)implements Serializable {
    private static final long serialVersionUID = 1L;
    public String getIdTransaccion() {
        return idTransaccion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public TipoTransaccion getTipoTransaccion() {
        return tipoTransaccion;
    }

    public Double getMonto() {
        return monto;
    }

    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public Cuenta getCuentaDestino() {
        return cuentaDestino;
    }
}
