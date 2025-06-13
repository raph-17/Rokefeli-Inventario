package rokefeli.model;

public class Insumo {
    private String codigo;
    private String descripcion;
    private int stockActual;
    private int stockMin;

    public Insumo(String codigo, String descripcion, int stockActual, int stockMin) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.stockActual = stockActual;
        this.stockMin = stockMin;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    
}
