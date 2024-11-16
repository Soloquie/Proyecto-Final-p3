package uniquindio.finalproject.mapping.dto;

import uniquindio.finalproject.Model.TipoCuenta;

import java.io.Serializable;

public record CuentaDto (
        String idCuenta,
        String nombreBanco,
        String numeroCuenta,
        TipoCuenta tipoCuenta) implements Serializable {
    private static final long serialVersionUID = 1L;
}
