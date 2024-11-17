package uniquindio.finalproject.Model;

import java.io.Serializable;

public class Cuenta implements Serializable {
    private static final long serialVersionUID = 1L;
    private String idCuenta;
    private String nombreBanco;
    private String numeroCuenta;
    private Usuario usuario;
    private TipoCuenta tipoCuenta;
    private Double saldo;

    public Cuenta(){}

    public Cuenta(String idCuenta, String nombreBanco, String numeroCuenta, TipoCuenta tipoCuenta, Usuario usuario, Double saldo) {
        this.idCuenta = idCuenta;
        this.nombreBanco = nombreBanco;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.usuario = usuario;
        this.saldo = saldo;
    }

    public String getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(String idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "idCuenta='" + idCuenta + '\'' +
                ", nombreBanco='" + nombreBanco + '\'' +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", usuario=" + usuario +
                ", tipoCuenta=" + tipoCuenta +
                ", saldo=" + saldo +
                '}';
    }
}