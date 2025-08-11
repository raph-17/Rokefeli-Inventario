package rokefeli.data;

import java.io.Serializable;
import java.util.LinkedList;

// Clases de objetos de lista
import rokefeli.model.MateriaPrima;
import rokefeli.model.Insumo;
import rokefeli.model.ProductoFinal;

public class InventarioData implements Serializable{
    private LinkedList<MateriaPrima> lotesMiel;
    private LinkedList<Insumo> insumos;
    private LinkedList<ProductoFinal> productosFinales;
    
    public InventarioData(LinkedList<MateriaPrima> lotesMiel, LinkedList<Insumo> insumos, LinkedList<ProductoFinal> productosFinales){
        this.lotesMiel = lotesMiel;
        this.insumos = insumos;
        this.productosFinales = productosFinales;
    }

    public LinkedList<MateriaPrima> getLotesMiel() {
        return lotesMiel;
    }

    public LinkedList<Insumo> getInsumos() {
        return insumos;
    }

    public LinkedList<ProductoFinal> getProductosFinales() {
        return productosFinales;
    }
    
}
