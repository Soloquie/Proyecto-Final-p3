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
    public String getIdCuenta() {
        return idCuenta;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Double getSaldo() {
        return saldo;
    }
}