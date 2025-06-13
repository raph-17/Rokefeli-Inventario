package rokefeli.model;

import java.time.LocalDate;

public class MPVariadaPorLote {
    private String descripcion; // Huaranga, ajo, cebolla, kion, totuma, propóleo, limón
    private LocalDate fechaCompra;
    private double cantKg;

    public MPVariadaPorLote(String descripcion, LocalDate fechaCompra, double cantKg) {
        this.descripcion = descripcion;
        this.fechaCompra = fechaCompra;
        this.cantKg = cantKg;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    
    
    
}
