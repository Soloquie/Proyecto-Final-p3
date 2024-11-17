package uniquindio.finalproject.mapping.dto;

import uniquindio.finalproject.Model.TipoCuenta;
import uniquindio.finalproject.Model.Usuario;

import java.io.Serializable;

public record CuentaDto (
        String idCuenta,
        String nombreBanco,
        String numeroCuenta,
        TipoCuenta tipoCuenta
        , Usuario usuario, Double saldo) implements Serializable {
    private static final long serialVersionUID = 1L;
}