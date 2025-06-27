package rokefeli.model;

public class ProductoFinal {
    private String sku;
    private String descripcion;    
    private String loteAsociado;
    private int stock;
    private String idLote;

    public ProductoFinal(String sku, String descripcion, String loteAsociado, String idLote, int stock) {
        this.sku = sku;
        this.descripcion = descripcion;
        this.loteAsociado = loteAsociado;
        this.idLote = idLote;
        this.stock = stock;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLoteAsociado() {
        return loteAsociado;
    }

    public void setLoteAsociado(String loteAsociado) {
        this.loteAsociado = loteAsociado;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getIdLote() {
        return idLote;
    }

    public void setIdLote(String idLote) {
        this.idLote = idLote;
    }

    
}
