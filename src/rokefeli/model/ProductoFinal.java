package rokefeli.model;

public class ProductoFinal {
    private String sku;
    private String descripcion;    
    private String loteAsociado;
    private int stock;
    private String idLote;

    public ProductoFinal(String sku, String descripcion, String loteAsociado, String idLote, int stock) {
     
        this.descripcion = descripcion;
        this.loteAsociado = loteAsociado;
        this.idLote = idLote;
        this.stock = stock;
    }

    public String getSku() {
        return sku;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getLoteAsociado() {
        return loteAsociado;
    }

    public String getIdLote() {
        return idLote;
    }

    public int getStock() {
        return stock;
    }

}
