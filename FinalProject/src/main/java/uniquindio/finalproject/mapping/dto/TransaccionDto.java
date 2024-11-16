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
}
