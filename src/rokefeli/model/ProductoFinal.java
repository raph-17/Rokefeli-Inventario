package rokefeli.model;

import java.io.Serializable;

public class ProductoFinal implements Serializable{
    private String sku;
    private String idLote;
    private String descripcion;
    private double precioVenta;
    private int stockActual;
    private int stockMin;

    public ProductoFinal(String sku, String idLote, String descripcion, double precioVenta, int stockActual, int stockMin) {
        this.sku = sku;
        this.idLote = idLote;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.stockActual = stockActual;
        this.stockMin = stockMin;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getIdLote() {
        return idLote;
    }

    public void setIdLote(String idLote) {
        this.idLote = idLote;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    public int getStockMin() {
        return stockMin;
    }

    public void setStockMin(int stockMin) {
        this.stockMin = stockMin;
    }

    
    // Métodos para ajustar el stock
    public void agregarStock(int cantidad) {
        if (cantidad > 0) {
            this.stockActual += cantidad;
        }
    }
    
     public boolean retirarStock(int cantidad) {
        if (cantidad > 0 && this.stockActual >= cantidad) {
            this.stockActual -= cantidad;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ProductoFinal{" +
               "sku='" + sku + '\'' +
               ", descripcion='" + descripcion + '\'' +
               ", precioVenta=" + precioVenta +
               ", stockActual=" + stockActual +
               ", stockMin=" + stockMin +
               '}';
    }
    
}
