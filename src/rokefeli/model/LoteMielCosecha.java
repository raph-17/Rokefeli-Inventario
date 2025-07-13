package rokefeli.model;

import java.time.LocalDate;
import java.io.Serializable;

public class LoteMielCosecha implements Serializable{
    private String idLote;
    private String floracion; // "Huarango", "Eucalipto", "Naranjo", "Polifloral" 
    private String origen;
    private LocalDate fechaCompra;
    private double cantKg;
    private String estado; // "En Reposo", "Pasteurizada", "Lista para Envasar"

    public LoteMielCosecha(String idLote, String floracion, String origen, LocalDate fechaCompra, double cantKg) {
        this.idLote = idLote;
        this.floracion = floracion;
        this.origen = origen;
        this.fechaCompra = fechaCompra;
        this.cantKg = cantKg;
        this.estado = "En Reposo";
    }

    public String getIdLote() {
        return idLote;
    }

    public void setIdLote(String idLote) {
        this.idLote = idLote;
    }

    public String getFloracion() {
        return floracion;
    }

    public void setFloracion(String floracion) {
        this.floracion = floracion;
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
    
    @Override
    public String toString() {
        return "LoteMielCosecha{" +
               "idLote='" + idLote + '\'' +
               ", floracion='" + floracion + '\'' +
               ", origen='" + origen + '\'' +
               ", fechaCompra=" + fechaCompra +
               ", cantKg=" + cantKg +
               ", estado='" + estado + '\'' +
               '}';
    }
    
}
