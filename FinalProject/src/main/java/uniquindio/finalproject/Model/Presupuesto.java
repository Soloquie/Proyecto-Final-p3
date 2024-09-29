package uniquindio.finalproject.Model;

public class Presupuesto {
    private String idPresupuesto;
    private String nombre;
    private Double montoTotal;
    private Double montoGastado;
    private Categoria categoria;

    public Presupuesto(String idPresupuesto, String nombre, Double montoTotal, Double montoGastado, Categoria categoria) {
        this.idPresupuesto = idPresupuesto;
        this.nombre = nombre;
        this.montoTotal = montoTotal;
        this.montoGastado = montoGastado;
        this.categoria = categoria;
    }

    public String getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(String idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Double getMontoGastado() {
        return montoGastado;
    }

    public void setMontoGastado(Double montoGastado) {
        this.montoGastado = montoGastado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
