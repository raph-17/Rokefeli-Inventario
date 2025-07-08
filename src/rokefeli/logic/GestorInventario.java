package rokefeli.logic;

// Manejo de archivos
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.nio.charset.StandardCharsets;

// Interfaces de Ususario
import rokefeli.ui.*;

// Manejo de fechas
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// LinkedList
import java.util.LinkedList;

// Clases Materia Prima, Insumos, Producto Final
import rokefeli.model.*;

// Clase contenedora para el inventario general
import rokefeli.data.InventarioData;

public class GestorInventario {
    public LinkedList<Insumo> inventarioInsumos = new LinkedList();
    public LinkedList<LoteMielCosecha> inventarioLotes = new LinkedList();
    public LinkedList<ProductoFinal> inventarioProductos = new LinkedList();
    private LocalDate fechaActualRegistro;
    private final String INVENTARIO_FILE_NAME = "inventario_completo.ser";
    
    // Constructor inicializando con la fecha actual
    public GestorInventario(){
        this.fechaActualRegistro = LocalDate.now();
    }
    
    /* INVENTARIO GENERAL */

    // Guardar todos los objetos del inventario
    public void guardarInventario(){
        
    }
    
    // Cargar todos los objetos del inventario
    public void cargarInventario(){
        
    }
    
    
    /* MATERIA PRIMA */
    
    // Autogeneración del id del Lote
    public String autogenerarIdLoteMiel(LocalDate fecha, String floracion){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("ddMMYY");
        String fl = null;
        switch(floracion){
            case "Huarango" -> fl = "MH";
            case "Eucalipto" -> fl = "ME";
            case "Naranjo" -> fl = "MN";
            case "Polifloral (S)" -> fl = "MS";
            case "Polifloral (C)" -> fl = "MC";
        }
        return fecha.format(formato)+fl;
    }
    
    // Obtener nombre para el archivo de teto en el que se hará el registro
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
            FileOutputStream fos = new FileOutputStream(nombreArchivo, true); 
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            PrintWriter pw = new PrintWriter(osw)){
                pw.print(entradaRegistro);
                System.out.println("DEBUG: Movimiento registrado en: " + nombreArchivo);
        }catch(IOException e){
            System.err.println("ERROR al escribir en el archivo de registro: " + e.getMessage());
        }
    }
    
    // Ingresar materia prima
    public LoteMielCosecha ingresarLoteMiel(InterfazRokefeli interfaz){
        DatosLoteMiel dialog = new DatosLoteMiel(interfaz);
        dialog.setVisible(true);
        
        LoteMielCosecha nuevoLote = dialog.getLoteMielCosecha();
        
        String id = autogenerarIdLoteMiel(LocalDate.now(), nuevoLote.getFloracion());
        nuevoLote.setIdLote(id);
        
        return nuevoLote;
    }
    
    // Mostrar todos los lotes
    public String mostrarLotes() {
        int totalLotes = 0;
        StringBuilder resultado = new StringBuilder("""
                MOSTRANDO TODOS LOS LOTES:
                ID LOTE - FLORACION - ORIGEN - FECHA INGRESO - CANTIDAD (KG) - ESTADO\n
                """);
        if (inventarioLotes.isEmpty()) {
            resultado.append("No hay lotes registrados.");
        } else {
            for (LoteMielCosecha lote : inventarioLotes) {
                totalLotes++;
                resultado.append(lote.getIdLote()).append(" - ")
                         .append(lote.getFloracion()).append(" - ")
                         .append(lote.getOrigen()).append(" - ")
                         .append(lote.getFechaCompra()).append(" - ")
                         .append(lote.getCantKg()).append(" - ")
                         .append(lote.getEstado()).append("\n");
            }
            resultado.append("\nTOTAL: "+totalLotes);
        }
        return resultado.toString();
    }

    // Buscar lotes por floración
    public String buscarMateriaPrima(String criterio) {
        int totalLotes = 0;
        StringBuilder resultados = new StringBuilder("""
            RESULTADOS DE BÚSQUEDA:
            ID LOTE - FLORACION - ORIGEN - FECHA INGRESO - CANTIDAD (KG) - ESTADO\n
            """);
        boolean encontrado = false;

        for (LoteMielCosecha lote : inventarioLotes) {
            if (lote.getIdLote().equalsIgnoreCase(criterio.trim()) || lote.getFloracion().toLowerCase().contains(criterio.toLowerCase().trim())) {
                totalLotes++;
                resultados.append(lote.getIdLote()).append(" - ")
                         .append(lote.getFloracion()).append(" - ")
                         .append(lote.getOrigen()).append(" - ")
                         .append(lote.getFechaCompra()).append(" - ")
                         .append(lote.getCantKg()).append(" - ")
                         .append(lote.getEstado()).append("\n");
                encontrado = true;
            }
        }
        resultados.append("\nTOTAL: "+totalLotes);

        if (!encontrado) {
            resultados.append("No se encontraron lotes que coincidan con el criterio.");
        }
        return resultados.toString();
    }

    // Transformar el estado de un lote
    public void transformarLote(String idLote, String nuevoEstado) {
        // Buscar el lote por idLote (debe coincidir con un LoteMielCosecha existente)
        LoteMielCosecha loteEncontrado = null;
        for (LoteMielCosecha lote : inventarioLotes) {
            if (lote.getIdLote().equals(idLote.trim())) {
                loteEncontrado = lote;
                break;
            }
        }
        loteEncontrado.setEstado(nuevoEstado);
    }

//    // Obtener la lista de IDs de lotes (para poblar el JComboBox)
//    public String[] getIdsLotes() {
//        String[] ids = new String[inventarioLotes.size()];
//        for (int i = 0; i < inventarioLotes.size(); i++) {
//            ids[i] = inventarioLotes.get(i).getIdLote();
//        }
//        return ids;
//    }

//    // Obtener información de un lote por ID (para mostrar en el diálogo)
//    public String getInfoLote(String idLote) {
//        for (LoteMielCosecha lote : inventarioLotes) {
//            if (lote.getIdLote().equals(idLote)) {
//                return "Lote: " + lote.getIdLote() + " | FloraciÃ³n: " + lote.getFloracion() +
//                       " | Estado actual: " + lote.getEstado();
//            }
//        } return "Lote no encontrado.";
//        
//    }
    
    //Comprobar que el lote no se repita
    public boolean repetirLote(String idlote){
        for(LoteMielCosecha lote : inventarioLotes){
            if(idlote.equals(lote.getIdLote())){
                return true;
            }
        }
        return false;
    }
    
    
    /* INSUMOS */
    
    // Inicializar insumos por defecto
    public void inicializarInsumosPorDefecto() {
        if (inventarioInsumos.isEmpty()) {
            inventarioInsumos.add(new Insumo("FRA1KP", "Frasco 1kg (plástico)", 0, 50));
            inventarioInsumos.add(new Insumo("FRA1KV", "Frasco 1kg (vidrio)", 0, 50));
            inventarioInsumos.add(new Insumo("FRA05KP", "Frasco 1/2kg (plástico)", 0, 50));
            inventarioInsumos.add(new Insumo("FRA05KV", "Frasco 1/2kg (vidrio)", 0, 50));
            inventarioInsumos.add(new Insumo("BOL1K", "Bolsa 1kg", 0, 50));
            inventarioInsumos.add(new Insumo("BOL05K", "Bolsa 1/2kg", 0, 50));
            inventarioInsumos.add(new Insumo("PRE1K", "Precinto 1kg", 0, 100));
            inventarioInsumos.add(new Insumo("PRE05K", "Precinto 1/2kg", 0, 100));
            inventarioInsumos.add(new Insumo("ETI_FRA1K", "Etiqueta Frasco 1kg", 0, 300));
            inventarioInsumos.add(new Insumo("ETI_FRA05K", "Etiqueta Frasco 1/2kg", 0, 300));
            inventarioInsumos.add(new Insumo("ETI_BOL1K", "Etiqueta Bolsa 1kg", 0, 300));
            inventarioInsumos.add(new Insumo("ETI_BOL05K", "Etiqueta Bolsa 1/2kg", 0, 300));
            inventarioInsumos.add(new Insumo("TAPA1KP", "Tapa frasco 1kg (plástico)", 0, 50));
            inventarioInsumos.add(new Insumo("TAPA1KV", "Tapa frasco 1kg (vidrio)", 0, 50));
            inventarioInsumos.add(new Insumo("TAPA05KP", "Tapa frasco 1/2kg (plástico)", 0, 50));
            inventarioInsumos.add(new Insumo("TAPA05KV", "Tapa frasco 1/2kg (vidrio)", 0, 50));

            System.out.println("DEBUG: Insumos por defecto inicializados.");
        }
    }

    // Añadir stock a insumo
    public void ingresarStockInsumo(InterfazRokefeli interfaz){
        DatosInsumo dialog = new DatosInsumo(interfaz);
        dialog.setVisible(true);
        int cantidad = dialog.getCantidad();
        String tipoInsumo = dialog.getTipoInsumo();
        
        for (Insumo insumo : inventarioInsumos){
            if(insumo.getDescripcion().equals(tipoInsumo)){
                insumo.agregarStock(cantidad);
            }
        }
    }
    
    // Mostrar totales de insumos
    public String mostrarInsumos() {
        if (inventarioInsumos.isEmpty()) {
            return "No hay insumos registrados.";
        }

        StringBuilder sb = new StringBuilder("TOTALES DE INSUMOS:\n");
        int totalGeneral = 0;

        for (Insumo insumo : inventarioInsumos) {
            sb.append(insumo.getCodigo()).append(" - ")
              .append(insumo.getDescripcion()).append(" - Stock actual: ")
              .append(insumo.getStockActual()).append(" unidades\n");
            totalGeneral += insumo.getStockActual();
        }

        sb.append("TOTAL GENERAL DE UNIDADES: ").append(totalGeneral);
        return sb.toString();
    }

    // Buscar insumo por código
    public String buscarInsumoPorCodigo(String codigoBuscado) {
        if (codigoBuscado == null || codigoBuscado.trim().isEmpty()) {
            return "Error: El código no puede estar vací­o.";
        }

        for (Insumo insumo : inventarioInsumos) {
            if (insumo.getCodigo().equalsIgnoreCase(codigoBuscado.trim())) {
                return "INSUMO ENCONTRADO:\n"
                        + "Código: " + insumo.getCodigo() + "\n"
                        + "Descripción: " + insumo.getDescripcion() + "\n"
                        + "Stock actual: " + insumo.getStockActual() + "\n"
                        + "Stock mínimo: " + insumo.getStockMin();
            }
        }

        return "No se encontró ningún insumo con el código: " + codigoBuscado;
    }
}
