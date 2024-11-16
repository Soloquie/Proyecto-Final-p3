package uniquindio.finalproject.Model;

import java.io.Serializable;
import java.util.LinkedList;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String usuarioID;
    private String nombre;
    private String correo;
    private String numeroTelefono;
    private String direccion;
    private Double saldoTotal;
    private String contraseña;
    private LinkedList<Cuenta> cuentasAsociadas;
    private LinkedList<Transaccion> transacciones;
    private LinkedList<Presupuesto> presupuestos;
    private LinkedList<Categoria> categoriasAsociadas;

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setTransacciones(LinkedList<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }

    public LinkedList<Presupuesto> getPresupuestos() {
        return presupuestos;
    }

    public void setPresupuestos(LinkedList<Presupuesto> presupuestos) {
        this.presupuestos = presupuestos;
    }

    public Usuario(String usuarioID, String nombre, String correo, String numeroTelefono, String direccion, Double saldoTotal, String contraseña) {
        this.usuarioID = usuarioID;
        this.nombre = nombre;
        this.correo = correo;
        this.numeroTelefono = numeroTelefono;
        this.direccion = direccion;
        this.saldoTotal = saldoTotal;
        this.cuentasAsociadas = new LinkedList<>();
        this.transacciones = new LinkedList<>();
        this.presupuestos = new LinkedList<>();
        this.contraseña = contraseña;
        this.categoriasAsociadas = new LinkedList<>();
    }

    public Usuario(){}

    public LinkedList<Cuenta> getCuentasAsociadas() {
        return cuentasAsociadas;
    }

    public void setCuentasAsociadas(LinkedList<Cuenta> cuentasAsociadas) {
        this.cuentasAsociadas = cuentasAsociadas;
    }

    public Double getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(Double saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    public String getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(String usuarioID) {
        this.usuarioID = usuarioID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LinkedList<Transaccion> getTransaccionesAsociadas() {
        return transacciones;
    }

    public void añadirTransaccion(Transaccion transaccion) {
        transacciones.add(transaccion);
    }

    public void añadirCuenta(Cuenta cuenta){
        cuentasAsociadas.add(cuenta);
    }

    public void añadirPresupuesto(Presupuesto presupuesto){ presupuestos.add(presupuesto); }

    public LinkedList<Categoria> getCategoriasAsociadas(){
        return categoriasAsociadas;
    }



    public void añadirCategoria(Categoria categoria){
        categoriasAsociadas.add(categoria);
    }

    public Cuenta getCuentaPorId(String idCuenta) {
        for(Cuenta cuenta: cuentasAsociadas){
            if(cuenta.getIdCuenta().equals(idCuenta)){
                return cuenta;
            }
        }
        return null;
    }

    public Categoria getCategoriaPorId(String idCategoria) {
        for(Categoria categoria : categoriasAsociadas){
            if(categoria.getIdCategoria().equals(idCategoria)){
                return categoria;
            }
        }
        return null;
    }

    public void setCategoriasAsociadas(LinkedList<Categoria> categoriasAsociadas) {
        this.categoriasAsociadas = categoriasAsociadas;
    }
}
