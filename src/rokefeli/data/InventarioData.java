package rokefeli.data;

import java.io.Serializable;
import java.util.LinkedList;

// Clases de objetos de lista
import rokefeli.model.LoteMielCosecha;
import rokefeli.model.Insumo;
import rokefeli.model.ProductoFinal;

public class InventarioData implements Serializable{
    private LinkedList<LoteMielCosecha> lotesMiel;
    private LinkedList<Insumo> insumos;
    private LinkedList<ProductoFinal> productosFinales;
    
    public InventarioData(LinkedList<LoteMielCosecha> lotesMiel, LinkedList<Insumo> insumos, LinkedList<ProductoFinal> productosFinales){
        this.lotesMiel = lotesMiel;
        this.insumos = insumos;
        this.productosFinales = productosFinales;
    }

    public LinkedList<LoteMielCosecha> getLotesMiel() {
        return lotesMiel;
    }

    public LinkedList<Insumo> getInsumos() {
        return insumos;
    }

    public LinkedList<ProductoFinal> getProductosFinales() {
        return productosFinales;
    }
    
}
