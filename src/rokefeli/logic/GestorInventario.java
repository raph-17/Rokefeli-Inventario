package rokefeli.logic;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import rokefeli.model.*;
import java.util.LinkedList;
import java.util.Scanner;

public class GestorInventario {
    private LinkedList<Insumo> inventarioInsumos = new LinkedList();
    public LinkedList<LoteMielCosecha> inventarioLotes = new LinkedList();
    private LinkedList<ProductoFinal> inventarioProductos = new LinkedList();
    private int contadorLotesAsociados = 1; // Para generar loteAsociado único
    private LocalDate fechaActualRegistro;
    
    // Constructor inicializando con la fecha actual
    public GestorInventario(){
        this.fechaActualRegistro = LocalDate.now();
    }
    
    private String getNombreRegistroMateriaPrima(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy_MM");
        String mesAnio = fechaActualRegistro.format(formato);
        return "Movimientos_materiaPrima_" + mesAnio + ".txt";
    }
    
    // Escribir registro de materia prima en txt
    public void registrarMovimientoMateriaPrima(String tipoMovimiento, String idItem, double cantidad, String unidad, String descripcionExtra){
        String nombreArchivo = getNombreRegistroMateriaPrima();
        LocalDateTime fechaHora = LocalDateTime.now();
        DateTimeFormatter fechaHoraFormato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        String entradaRegistro = String.format("%s, %s, %s, %.2f %s, %s%n", 
                fechaHora.format(fechaHoraFormato), tipoMovimiento, idItem, cantidad, unidad, descripcionExtra);
        
        try(
            FileWriter fw = new FileWriter(nombreArchivo, true); 
            PrintWriter pw = new PrintWriter(fw)){
                pw.print(entradaRegistro);
                System.out.println("DEBUG: Movimiento registrado en: " + nombreArchivo);
        }catch(IOException e){
            System.err.println("ERROR al escribir en el archivo de registro: " + e.getMessage());
        }
    }
    
    // Método para mostrar todos los lotes
    public String mostrarLotes() {
        StringBuilder resultado = new StringBuilder("""
                MOSTRANDO TODOS LOS LOTES:
                ID LOTE - FLORACION - ORIGEN - FECHA INGRESO - CANTIDAD (KG) - ESTADO\n
                """);
        if (inventarioLotes.isEmpty()) {
            resultado.append("No hay lotes registrados.");
        } else {
            for (LoteMielCosecha lote : inventarioLotes) {
                resultado.append(lote.getIdLote()).append(" - ")
                         .append(lote.getFloracion()).append(" - ")
                         .append(lote.getOrigen()).append(" - ")
                         .append(lote.getFechaCompra()).append(" - ")
                         .append(lote.getCantKg()).append(" - ")
                         .append(lote.getEstado()).append("\n");
            }
        }
        return resultado.toString();
    }

    // Método para buscar lotes por floración
    public String buscarPorFloracion(String floracionBuscada) {
        if (floracionBuscada == null || floracionBuscada.trim().isEmpty()) {
            return "Error: La floración buscada no puede ser vacía.";
        }

        floracionBuscada = floracionBuscada.trim().toLowerCase();
        StringBuilder resultado = new StringBuilder("""
                RESULTADOS DE BÚSQUEDA POR FLORACIÓN: """ + floracionBuscada.toUpperCase() + """
                ID LOTE - FLORACION - ORIGEN - FECHA INGRESO - CANTIDAD (KG) - ESTADO\n
                """);
        boolean encontrado = false;
        for (LoteMielCosecha lote : inventarioLotes) {
            if (lote.getFloracion().toLowerCase().contains(floracionBuscada)) {
                resultado.append(lote.getIdLote()).append(" - ")
                         .append(lote.getFloracion()).append(" - ")
                         .append(lote.getOrigen()).append(" - ")
                         .append(lote.getFechaCompra()).append(" - ")
                         .append(lote.getCantKg()).append(" - ")
                         .append(lote.getEstado()).append("\n");
                encontrado = true;
            }
        }
        if (!encontrado) {
            resultado.append("No se encontraron lotes con la floración especificada.");
        }
        return resultado.toString();
    }

    // Método para transformar el estado de un lote
    public String transformarLote(String idLote, String nuevoEstado) {
        if (idLote == null || idLote.trim().isEmpty()) {
            return "Error: El ID del lote no puede ser vacío.";
        }
        if (nuevoEstado == null || nuevoEstado.trim().isEmpty()) {
            return "Error: El estado seleccionado no puede ser vacío.";
        }

        // Buscar el lote por idLote (debe coincidir con un LoteMielCosecha existente)
        LoteMielCosecha loteEncontrado = null;
        for (LoteMielCosecha lote : inventarioLotes) {
            if (lote.getIdLote().equals(idLote.trim())) {
                loteEncontrado = lote;
                break;
            }
        }

        if (loteEncontrado == null) {
            return "Error: Lote con ID " + idLote + " no encontrado.";
        }

        String estadoActual = loteEncontrado.getEstado();
        // Validar transiciones de estado válidas
        if (nuevoEstado.equals("En Reposo")) {
            return "Error: No se puede revertir a estado En Reposo.";
        } else if (nuevoEstado.equals("Pasteurizada")) {
            if (!estadoActual.equals("En Reposo")) {
                return "Error: Solo se puede pasteurizar un lote en estado En Reposo.";
            }
            loteEncontrado.setEstado("Pasteurizada");
            return "Lote " + idLote + " transformado a estado: Pasteurizada.";
        } else if (nuevoEstado.equals("Lista para Envasar")) {
            if (!estadoActual.equals("Pasteurizada")) {
                return "Error: Solo se puede preparar para envasar un lote en estado Pasteurizada.";
            }
            loteEncontrado.setEstado("Lista para Envasar");
            String sku = "PF-" + String.format("%03d", contadorLotesAsociados);
            String loteAsociado = "PF-LOTE-" + String.format("%03d", contadorLotesAsociados++);
            String descripcion = "Miel " + loteEncontrado.getFloracion() + " Envasada";
            int stockInicial = (int) (loteEncontrado.getCantKg() / 0.5); // Suponiendo envases de 0.5 kg
            ProductoFinal nuevoProducto = new ProductoFinal(sku, descripcion, loteAsociado, idLote, stockInicial);
            inventarioProductos.add(nuevoProducto);
            return "Lote " + idLote + " transformado a estado: Lista para Envasar.\n" +
                   "Producto final creado: " + descripcion + " (Lote Asociado: " + loteAsociado + ", Stock: " + stockInicial + " envases).";
        } else {
            return "Error: Estado " + nuevoEstado + " no válido.";
        }
    }

    // Getter para obtener la lista de IDs de lotes (para poblar el JComboBox)
    public String[] getIdsLotes() {
        String[] ids = new String[inventarioLotes.size()];
        for (int i = 0; i < inventarioLotes.size(); i++) {
            ids[i] = inventarioLotes.get(i).getIdLote();
        }
        return ids;
    }

    // Método para obtener información de un lote por ID (para mostrar en el diálogo)
    public String getInfoLote(String idLote) {
        for (LoteMielCosecha lote : inventarioLotes) {
            if (lote.getIdLote().equals(idLote)) {
                return "Lote: " + lote.getIdLote() + " | Floración: " + lote.getFloracion() +
                       " | Estado actual: " + lote.getEstado();
            }
        } return "Lote no encontrado.";
        
    }
    
    //Método para comprobar que el lote no se repita
    public boolean repetirLote(String idlote){
        for(LoteMielCosecha lote : inventarioLotes){
            if(idlote.equals(lote.getIdLote())){
                return true;
            }
        }
        return false;
    }
 
}
