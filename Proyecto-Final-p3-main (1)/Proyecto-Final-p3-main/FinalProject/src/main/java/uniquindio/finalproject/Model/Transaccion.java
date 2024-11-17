package uniquindio.finalproject.Model;

import java.io.Serializable;
import java.time.LocalDate;

public class Transaccion implements Serializable {
    private static final long serialVersionUID = 1L;
    private Usuario usuario;
    private String idTransaccion;
    private LocalDate fecha;
    private TipoTransaccion tipoTransaccion;
    private Double monto;
    private String descripcion;
    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;
    private Categoria categoria;

    public Transaccion(Usuario usuario, String idTransaccion, LocalDate fecha, TipoTransaccion tipoTransaccion, Double monto, String descripcion, Cuenta cuentaOrigen, Cuenta cuentaDestino, Categoria categoria) {
        this.usuario = usuario;
        this.idTransaccion = idTransaccion;
        this.fecha = fecha;
        this.tipoTransaccion = tipoTransaccion;
        this.monto = monto;
        this.descripcion = descripcion;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.categoria = categoria;
        realizarTransaccion();
    }

    public Transaccion() {}

    private void realizarTransaccion() {
        for(Cuenta cuenta : usuario.getCuentasAsociadas()){
            if(usuario.buscarCuentaPorID(cuentaOrigen.getIdCuenta())){
                if(cuentaOrigen.getSaldo()<=monto ){
                    cuentaDestino.setSaldo(cuentaDestino.getSaldo()+monto);
                    cuentaOrigen.setSaldo(cuentaOrigen.getSaldo()-monto);
                    usuario.setSaldoTotal(usuario.getSaldoTotal()-monto);
                }
                else{System.out.println("No hay suficientes fondos para realizar la transaccion");}
            }
            else{System.out.println("No se ha encontrado la cuenta del usuario");}
        }
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public TipoTransaccion getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(TipoTransaccion tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(Cuenta cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public Cuenta getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(Cuenta cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public Categoria getCategoria() {return categoria;}

    public void setCategoria(Categoria categoria) {this.categoria = categoria;}

    public Usuario getUsuario(){
        return usuario;
    }
    public void setUsuario(Usuario usuario){this.usuario = usuario;}

    @Override
    public String toString() {
        return "Transaccion{" +
                "usuario=" + usuario +
                ", idTransaccion='" + idTransaccion + '\'' +
                ", fecha=" + fecha +
                ", tipoTransaccion=" + tipoTransaccion +
                ", monto=" + monto +
                ", descripcion='" + descripcion + '\'' +
                ", cuentaOrigen=" + cuentaOrigen +
                ", cuentaDestino=" + cuentaDestino +
                ", categoria=" + categoria +
                '}';
    }
}
