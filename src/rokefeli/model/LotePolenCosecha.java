package rokefeli.model;

import java.time.LocalDate;

public class LotePolenCosecha {
    private String idLote;
    private String tipo; // "Costa", "Sierra"
    private String origen;
    private LocalDate fechaCompra;
    private double cantKg;
    private String estado; // "Húmedo" (recién llegado), "Seco", "Filtrado y Listo para Envasar"

    public LotePolenCosecha(String idLote, String tipo, String origen, LocalDate fechaCompra, double cantKg) {
        this.idLote = idLote;
        this.tipo = tipo;
        this.origen = origen;
        this.fechaCompra = fechaCompra;
        this.cantKg = cantKg;
        this.estado = "Húmedo";
    }

    public String getIdLote() {
        return idLote;
    }

    public void setIdLote(String idLote) {
        this.idLote = idLote;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getCantKg() {
        return cantKg;
    }

    public void setCantKg(double cantKg) {
        this.cantKg = cantKg;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
