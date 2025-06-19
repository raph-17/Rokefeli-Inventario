package rokefeli.logic;

import java.time.LocalDate;
import rokefeli.model.*;
import java.util.LinkedList;
import java.util.Scanner;

public class GestorInventario {
    private LinkedList<Insumo> inventarioInsumos = new LinkedList();
    public LinkedList<LoteMielCosecha> inventarioLotes = new LinkedList();
    private LinkedList<ProductoFinal> inventarioProductos = new LinkedList();
    
    static LoteMielCosecha nuevoLote = new LoteMielCosecha("id01", "eucalipto", "sierra", LocalDate.now(), 300);
    
    public void mostrarLotes() {
       for (LoteMielCosecha lote: inventarioLotes) {
           System.out.println(lote.getIdLote()+" - "+lote.getCantKg()+" - "+lote.getEstado()+" - "+lote.getFloracion()+" - "+lote.getOrigen()+" - "+lote.getFechaCompra());
       }
    }
    
    public void main(String[] args) {
        inventarioLotes.add(nuevoLote);
        mostrarLotes();
    }
    
    
}
