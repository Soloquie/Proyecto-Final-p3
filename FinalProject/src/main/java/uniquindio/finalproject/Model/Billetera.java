package uniquindio.finalproject.Model;

import java.io.Serializable;

public class Billetera implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombreUsuario;
    private double saldo;

    // Constructor de prueba
    public Billetera(String nombreUsuario, double saldo) {
        this.nombreUsuario = nombreUsuario;
        this.saldo = saldo;
    }

    // Getters y setters
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
