package rokefeli.logic;

// Manejo de archivos
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.InputStreamReader;
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

    // Guardar todos los objetos del inventario en archivo
    public void guardarInventario(){
        try (FileOutputStream fos = new FileOutputStream(INVENTARIO_FILE_NAME);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            // Crea un objeto contenedor con todas tus listas actuales
            InventarioData data = new InventarioData(inventarioLotes, inventarioInsumos, inventarioProductos);
            oos.writeObject(data); // Guarda el objeto contenedor completo
            System.out.println("DEBUG: Todos los inventarios guardados exitosamente en " + INVENTARIO_FILE_NAME);
        } catch (IOException e) {
            System.err.println("ERROR al guardar todos los inventarios: " + e.getMessage());
        }
    }
    
    // Cargar todos los objetos del inventario desde el archivo
    public void cargarInventario(){
        File file = new File(INVENTARIO_FILE_NAME);
        if (!file.exists()) {
            System.out.println("DEBUG: Archivo de inventario completo '" + INVENTARIO_FILE_NAME + "' no encontrado. Se inician listas vacías.");
            inventarioLotes = new LinkedList<>();
            inventarioInsumos = new LinkedList<>();
            inventarioProductos = new LinkedList<>();
            inicializarInsumosPorDefecto();
            return;
        }

        try (FileInputStream fis = new FileInputStream(INVENTARIO_FILE_NAME);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            InventarioData data = (InventarioData) ois.readObject(); // Carga el objeto contenedor
            // Asigna las listas cargadas a los atributos de GestorInventario
            inventarioLotes = data.getLotesMiel();
            inventarioInsumos = data.getInsumos();
            inventarioProductos = data.getProductosFinales();
            System.out.println("DEBUG: Todos los inventarios cargados exitosamente desde " + INVENTARIO_FILE_NAME);

            // Asegura que los insumos por defecto estén presentes si por alguna razón el archivo cargado no los contenía
            if (inventarioInsumos.isEmpty()) {
                inicializarInsumosPorDefecto();
            }

        } catch (FileNotFoundException e) {
            System.err.println("ERROR: Archivo de inventario no encontrado al cargar (debería ser cubierto por file.exists()): " + e.getMessage());
            inicializarListasVaciasYInsumosDefecto();
        } catch (IOException e) {
            System.err.println("ERROR al leer el inventario completo: " + e.getMessage());
            inicializarListasVaciasYInsumosDefecto();
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: No se encontró la clase del objeto al cargar el inventario (posible versión incompatible): " + e.getMessage());
            inicializarListasVaciasYInsumosDefecto();
        } catch (ClassCastException e) {
            System.err.println("ERROR: Tipo de objeto inesperado en el archivo de inventario: " + e.getMessage());
            inicializarListasVaciasYInsumosDefecto();
        }
    }

    // Método auxiliar para inicializar listas en caso de error de carga
    private void inicializarListasVaciasYInsumosDefecto() {
        inventarioLotes = new LinkedList<>();
        inventarioInsumos = new LinkedList<>();
        inventarioProductos = new LinkedList<>();
        inicializarInsumosPorDefecto();
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
    public String getNombreRegistroMateriaPrima(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy_MM");
        String mesAnio = fechaActualRegistro.format(formato);
        return "Movimientos_Materia_Prima_" + mesAnio + ".txt";
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
            resultado.append("\nTOTAL: ").append(totalLotes);
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
        // Asegurarse de que cada insumo por defecto exista en la lista
        // Solo se añadirán si no están ya presentes
        if (buscarInsumoPorDescripcion("Frasco 1kg (plástico)") == null) {
            inventarioInsumos.add(new Insumo("FRA1KP", "Frasco 1kg (plástico)", 0, 50));
        }
        if (buscarInsumoPorDescripcion("Frasco 1kg (vidrio)") == null) {
            inventarioInsumos.add(new Insumo("FRA1KV", "Frasco 1kg (vidrio)", 0, 50));
        }
        if (buscarInsumoPorDescripcion("Frasco 1/2kg (plástico)") == null) {
            inventarioInsumos.add(new Insumo("FRA05KP", "Frasco 1/2kg (plástico)", 0, 50));
        }
        if (buscarInsumoPorDescripcion("Frasco 1/2kg (vidrio)") == null) {
            inventarioInsumos.add(new Insumo("FRA05KV", "Frasco 1/2kg (vidrio)", 0, 50));
        }
        if (buscarInsumoPorDescripcion("Bolsa 1kg") == null) {
            inventarioInsumos.add(new Insumo("BOL1K", "Bolsa 1kg", 0, 50));
        }
        if (buscarInsumoPorDescripcion("Bolsa 1/2kg") == null) {
            inventarioInsumos.add(new Insumo("BOL05K", "Bolsa 1/2kg", 0, 50));
        }
        if (buscarInsumoPorDescripcion("Precinto 1kg") == null) {
            inventarioInsumos.add(new Insumo("PRE1K", "Precinto 1kg", 0, 100));
        }
        if (buscarInsumoPorDescripcion("Precinto 1/2kg") == null) {
            inventarioInsumos.add(new Insumo("PRE05K", "Precinto 1/2kg", 0, 100));
        }
        if (buscarInsumoPorDescripcion("Etiqueta Frasco 1kg") == null) {
            inventarioInsumos.add(new Insumo("ETI_FRA1K", "Etiqueta Frasco 1kg", 0, 300));
        }
        if (buscarInsumoPorDescripcion("Etiqueta Frasco 1/2kg") == null) {
            inventarioInsumos.add(new Insumo("ETI_FRA05K", "Etiqueta Frasco 1/2kg", 0, 300));
        }
        if (buscarInsumoPorDescripcion("Etiqueta Bolsa 1kg") == null) {
            inventarioInsumos.add(new Insumo("ETI_BOL1K", "Etiqueta Bolsa 1kg", 0, 300));
        }
        if (buscarInsumoPorDescripcion("Etiqueta Bolsa 1/2kg") == null) {
            inventarioInsumos.add(new Insumo("ETI_BOL05K", "Etiqueta Bolsa 1/2kg", 0, 300));
        }
        if (buscarInsumoPorDescripcion("Tapa Frasco 1kg (plástico)") == null) {
            inventarioInsumos.add(new Insumo("TAPA1KP", "Tapa Frasco 1kg (plástico)", 0, 50));
        }
        if (buscarInsumoPorDescripcion("Tapa Frasco 1kg (vidrio)") == null) {
            inventarioInsumos.add(new Insumo("TAPA1KV", "Tapa Frasco 1kg (vidrio)", 0, 50));
        }
        if (buscarInsumoPorDescripcion("Tapa Frasco 1/2kg (plástico)") == null) {
            inventarioInsumos.add(new Insumo("TAPA05KP", "Tapa Frasco 1/2kg (plástico)", 0, 50));
        }
        if (buscarInsumoPorDescripcion("Tapa Frasco 1/2kg (vidrio)") == null) {
            inventarioInsumos.add(new Insumo("TAPA05KV", "Tapa Frasco 1/2kg (vidrio)", 0, 50));
        }

        System.out.println("DEBUG: Insumos por defecto inicializados/verificados.");
    }

    // Buscar insumo por descripcion
    public Insumo buscarInsumoPorDescripcion(String descripcion) {
        for (Insumo insumo : inventarioInsumos) {
            if (insumo.getDescripcion().equals(descripcion)) {
                return insumo;
            }
        }
        return null;
    }

    // Añadir stock a insumo
    public String añadirStockInsumo(String descripcionInsumo, int cantidad) {
        if (cantidad <= 0) {
            return "La cantidad a añadir debe ser positiva.";
        }

        Insumo insumo = buscarInsumoPorDescripcion(descripcionInsumo);
        if (insumo != null) {
            insumo.agregarStock(cantidad); // Llama al método agregarStock de la clase Insumo
            registrarMovimientoInsumo("ENTRADA", descripcionInsumo, cantidad);
            return "Se añadieron " + cantidad + " unidades a '" + descripcionInsumo + "'. Nuevo stock: " + insumo.getStockActual();
        } else {
            return "Insumo '" + descripcionInsumo + "' no encontrado.";
        }
    }
    
    // Retirar stock de insumo
    public String retirarStockInsumo(String codigo, int cantidad) {
        for (Insumo insumo : inventarioInsumos) {
            if (insumo.getCodigo().equals(codigo)) {
                if (insumo.retirarStock(cantidad)) {
                    registrarMovimientoInsumo("SALIDA", insumo.getDescripcion(), cantidad);
                    return "Stock de " + insumo.getDescripcion() + " actualizado. Nuevo stock: " + insumo.getStockActual();
                } else {
                    return "Error: Stock insuficiente para " + insumo.getDescripcion() + ". Stock actual: " + insumo.getStockActual();
                }
            }
        }
        return "Error: Insumo con código " + codigo + " no encontrado.";
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
    
    // Crear nombre de archivo para registro
    public String getNombreArchivoRegistroInsumos() {
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        return "Movimientos_Insumos_" + hoy.format(formatter) + ".txt";
    }
    
    // Registrar movimientos insumos en txt
    public void registrarMovimientoInsumo(String tipoMovimiento, String descripcionInsumo, int cantidad) {
        String nombreArchivo = getNombreArchivoRegistroInsumos();
        
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(nombreArchivo, true), StandardCharsets.UTF_8))) {
            
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaHoraFormateada = ahora.format(formatter);

            String entradaRegistro = String.format("%s | Tipo: %s | Insumo: %s | Cantidad: %d",
                                                    fechaHoraFormateada,
                                                    tipoMovimiento, // "ENTRADA" o "SALIDA"
                                                    descripcionInsumo,
                                                    cantidad);
            
            pw.println(entradaRegistro);
            System.out.println("DEBUG: Movimiento de insumo registrado en: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("ERROR al escribir en el archivo de movimientos de insumos (" + nombreArchivo + "): " + e.getMessage());
        }
    }

    
    
    /* PRODUCTOS FINALES */

    // Método para obtener solo los lotes listos para el envasado
    public String[] getIdsLotesListosParaEnvasar() {
        return inventarioLotes.stream()
                .filter(lote -> lote.getEstado().equals("Lista para Envasar"))
                .map(LoteMielCosecha::getIdLote)
                .toArray(String[]::new);
    }

    // Método principal para crear el producto final
    public String crearProductoFinal(String idLote, String tipoProducto, int cantidad) {
        // 1. Encontrar el lote de miel
        LoteMielCosecha loteSeleccionado = null;
        for (LoteMielCosecha lote : inventarioLotes) {
            if (lote.getIdLote().equals(idLote)) {
                loteSeleccionado = lote;
                break;
            }
        }
        if (loteSeleccionado == null) {
            return "Error: Lote de miel no encontrado.";
        }

        // 2. Definir los insumos y la cantidad de miel necesaria
        double mielRequeridaKg = 0;
        double precioVenta = 0;
        String[] codigosInsumosRequeridos;

        switch (tipoProducto) {
            case "Frasco 1kg (vidrio)":
                precioVenta = 25.50;
                mielRequeridaKg = 1.0 * cantidad;
                codigosInsumosRequeridos = new String[]{"FRA1KV", "TAPA1KV", "ETI_FRA1K", "PRE1K"};
                break;
            case "Frasco 1/2kg (vidrio)":
                precioVenta = 15.40;
                mielRequeridaKg = 0.5 * cantidad;
                codigosInsumosRequeridos = new String[]{"FRA05KV", "TAPA05KV", "ETI_FRA05K", "PRE05K"};
                break;
            case "Frasco 1kg (plástico)":
                precioVenta = 25.50;
                mielRequeridaKg = 1.0 * cantidad;
                codigosInsumosRequeridos = new String[]{"FRA1KP", "TAPA1KP", "ETI_FRA1K", "PRE1K"};
                break;
            case "Frasco 1/2kg (plástico)":
                precioVenta = 15.40;
                mielRequeridaKg = 0.5 * cantidad;
                codigosInsumosRequeridos = new String[]{"FRA05KP", "TAPA05KP", "ETI_FRA05K", "PRE05K"};
                break;
            case "Bolsa 1kg":
                precioVenta = 23.00;
                mielRequeridaKg = 1.0 * cantidad;
                codigosInsumosRequeridos = new String[]{"BOL1K", "ETI_BOL1K"};
                break;
            case "Bolsa 1/2kg":
                precioVenta = 13.30;
                mielRequeridaKg = 0.5 * cantidad;
                codigosInsumosRequeridos = new String[]{"BOL05K", "ETI_BOL05K"};
                break;
            default:
                return "Error: Tipo de producto no reconocido.";
        }

        // 3. Validar si hay suficiente miel y stock de insumos
        if (loteSeleccionado.getCantKg() < mielRequeridaKg) {
            return "Error: No hay suficiente miel en el lote. Requerido: " + mielRequeridaKg + "kg, Disponible: " + loteSeleccionado.getCantKg() + "kg.";
        }
        for (String codigoInsumo : codigosInsumosRequeridos) {
            boolean insumoEncontrado = false;
            for (Insumo insumo : inventarioInsumos) {
                if (insumo.getCodigo().equals(codigoInsumo)) {
                    insumoEncontrado = true;
                    if (insumo.getStockActual() < cantidad) {
                        return "Error: Stock insuficiente para el insumo '" + insumo.getDescripcion() + "'. Requerido: " + cantidad + ", Disponible: " + insumo.getStockActual() + ".";
                    }
                    break;
                }
            }
            if (!insumoEncontrado) {
                return "Error: El insumo con código '" + codigoInsumo + "' no fue encontrado en el inventario de insumos.";
            }
        }

        // 4. Si todo es correcto, descontar la miel y los insumos
        loteSeleccionado.setCantKg(loteSeleccionado.getCantKg() - mielRequeridaKg);
        for (String codigoInsumo : codigosInsumosRequeridos) {
            for (Insumo insumo : inventarioInsumos) {
                if (insumo.getCodigo().equals(codigoInsumo)) {
                    insumo.retirarStock(cantidad);
                    registrarMovimientoInsumo("SALIDA", insumo.getDescripcion(), cantidad);
                    break;
                }
            }
        }

        // 5. Crear o actualizar el producto final en el inventario
        boolean productoExistente = false;
        for (ProductoFinal pf : inventarioProductos) {
            if (pf.getDescripcion().equals(tipoProducto) && pf.getIdLote().equals(idLote)) {
                pf.setStockActual(pf.getStockActual() + cantidad);
                productoExistente = true;
                break;
            }
        }
        if (!productoExistente) {
            // Usamos la descripción como SKU por simplicidad
            ProductoFinal nuevoProducto = new ProductoFinal(tipoProducto, idLote,tipoProducto, precioVenta, cantidad, 0);
            inventarioProductos.add(nuevoProducto);
        }

        // Obtener la descripción y SKU del producto recién creado para el registro
        ProductoFinal productoCreado = buscarProductoFinalPorLoteYTipo(idLote, tipoProducto); // Asumiendo que tienes un método para buscarlo
        if (productoCreado != null) {
            registrarMovimientoProductoFinal("CREACION", productoCreado.getDescripcion(), cantidad, productoCreado.getSku(), idLote);
        }

        return "¡Éxito! Se han creado " + cantidad + " unidades de '" + tipoProducto + "'.";
    }
    
    // Buscar por idLote y tipo
    public ProductoFinal buscarProductoFinalPorLoteYTipo(String idLote, String descripcion) {
        for (ProductoFinal pf : inventarioProductos) {
            // Si el lote y la descripción ingresada coinciden con los del lote hacer un return
            if (pf.getIdLote() != null && pf.getIdLote().equals(idLote) &&
                pf.getDescripcion() != null && pf.getDescripcion().equals(descripcion)) {
                return pf;
            }
        }
        return null; // Si no se encuentra ningún ProductoFinal que coincida
    }
    
    // Método para mostrar los productos finales en la interfaz
    public String mostrarProductosFinales() {
        if (inventarioProductos.isEmpty()) {
            return "No hay productos finales registrados.";
        }

        StringBuilder sb = new StringBuilder("INVENTARIO DE PRODUCTOS FINALES:\n");
        sb.append("SKU/DESCRIPCIÓN - LOTE DE ORIGEN - PRECIO DE VENTA - STOCK ACTUAL\n");
        
        int totalGeneral = 0;
        for (ProductoFinal producto : inventarioProductos) {
            sb.append(producto.getDescripcion()).append(" - ")
              .append(producto.getIdLote()).append(" - ")
              .append(producto.getPrecioVenta()).append(" - ")
              .append(producto.getStockActual()).append(" unidades\n");
            totalGeneral += producto.getStockActual();
        }

        sb.append("\nTOTAL GENERAL DE UNIDADES: ").append(totalGeneral);
        return sb.toString();
    }
    
    // Buscar productos finales por criterio (descripción)
    public String buscarProductoFinal(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return "Criterio de búsqueda no puede estar vacío.";
        }
        
        StringBuilder resultados = new StringBuilder("RESULTADOS DE BÚSQUEDA DE PRODUCTOS FINALES:\n");
        resultados.append("SKU/DESCRIPCIÓN - LOTE DE ORIGEN - STOCK ACTUAL\n");
        
        boolean encontrado = false;
        String criterioBusqueda = criterio.trim().toLowerCase();

        for (ProductoFinal producto : inventarioProductos) {
            // Buscamos por la descripción
            if (producto.getDescripcion().toLowerCase().contains(criterioBusqueda)) {
                resultados.append(producto.getDescripcion()).append(" - ")
                          .append(producto.getIdLote()).append(" - ")
                          .append(producto.getStockActual()).append(" unidades\n");
                encontrado = true;
            }
        }

        if (!encontrado) {
            resultados.append("No se encontraron productos que coincidan con '").append(criterio).append("'.");
        }
        
        return resultados.toString();
    }
    
    // Crear archivo txt para registro producto final
    public String getNombreArchivoRegistroProductosFinales() {
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        return "Movimientos_Productos_Finales_" + hoy.format(formatter) + ".txt";
    }
    
    // Método para guardar los Productos Finales en un archivo de texto
    public void registrarMovimientoProductoFinal(String tipoMovimiento, String descripcionProducto, int cantidad, String skuProducto, String idLoteAsociado) {
        String nombreArchivo = getNombreArchivoRegistroProductosFinales(); // Obtiene el nombre del archivo con un método

        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(nombreArchivo, true), StandardCharsets.UTF_8))) {
            
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaHoraFormateada = ahora.format(formatter);

            // Construye la entrada del registro
            StringBuilder entradaRegistro = new StringBuilder();
            entradaRegistro.append(String.format("%s | Tipo: %s | Producto: %s | Cantidad: %d",
                                                    fechaHoraFormateada,
                                                    tipoMovimiento,
                                                    descripcionProducto,
                                                    cantidad));
            if (skuProducto != null && !skuProducto.isEmpty()) {
                entradaRegistro.append(" | SKU: ").append(skuProducto);
            }
            if (idLoteAsociado != null && !idLoteAsociado.isEmpty()) {
                entradaRegistro.append(" | Lote: ").append(idLoteAsociado);
            }
            
            pw.println(entradaRegistro.toString());
            System.out.println("DEBUG: Movimiento de producto final registrado en: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("ERROR al escribir en el archivo de movimientos de productos finales (" + nombreArchivo + "): " + e.getMessage());
        }
    }
    
    
    /* VENTAS */

    // Obtiene las descripciones de productos con stock para llenar el menú de venta
    public String[] getDescripcionesProductosParaVenta() {
        return inventarioProductos.stream()
                .filter(p -> p.getStockActual() > 0)
                .map(ProductoFinal::getDescripcion)
                .toArray(String[]::new);
    }

    // Registra el movimiento de la venta en un archivo de texto
    private void registrarMovimientoVenta(String producto, int cantidad, String comprador) {
        DateTimeFormatter mesAnioFormato = DateTimeFormatter.ofPattern("yyyy_MM");
        String nombreArchivo = "Movimientos_Ventas_" + fechaActualRegistro.format(mesAnioFormato) + ".txt";
        
        LocalDateTime fechaHora = LocalDateTime.now();
        DateTimeFormatter fechaHoraFormato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String entradaRegistro = String.format("%s, DESPACHOS, %s, %d uds, Comprador: %s%n",
                fechaHora.format(fechaHoraFormato), producto, cantidad, comprador);

        try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File(nombreArchivo), true))) {
            pw.print(entradaRegistro);
            System.out.println("DEBUG: Despacho registrada en: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("ERROR al escribir en el archivo de ventas: " + e.getMessage());
        }
    }

    // Procesa la venta, valida el stock y descuenta si es exitosa
    public String procesarVenta(String productoDesc, int cantidad, String comprador) {
        // 1. Buscar el producto final en el inventario
        ProductoFinal productoAVender = null;
        for (ProductoFinal pf : inventarioProductos) {
            if (pf.getDescripcion().equals(productoDesc)) {
                productoAVender = pf;
                break;
            }
        }

        if (productoAVender == null) {
            return "Error: Producto no encontrado.";
        }

        // 2. Validar si hay stock suficiente
        if (productoAVender.getStockActual() < cantidad) {
            return "Error: Stock insuficiente para realizar la venta.\n"
                    + "Requerido: " + cantidad + ", Disponible: " + productoAVender.getStockActual();
        }

        // 3. Si hay stock, realizar la venta
        productoAVender.setStockActual(productoAVender.getStockActual() - cantidad);

        // 4. Registrar la venta en el archivo de texto
        registrarMovimientoVenta(productoDesc, cantidad, comprador);
        
        // 5. Registrar salida de producto final
        registrarMovimientoProductoFinal("DESPACHO", productoDesc, cantidad, productoAVender.getSku(), productoAVender.getIdLote());
        
        return "¡Despacho exitoso! Se despacharon " + cantidad + " unidades a " + comprador + ".";
    }
    
    
    /* PESTAÑA REGISTRO*/
    
    public String filtrarRegistros(String nombreArchivo, String textoBusqueda, LocalDate fechaDesde, LocalDate fechaHasta) {
        StringBuilder resultadoFiltrado = new StringBuilder();
        File archivo = new File(nombreArchivo); // Crea un objeto File para verificar la existencia

        if (!archivo.exists()) {
            return "El archivo de registros ('" + nombreArchivo + "') no existe aún o no se pudo encontrar.";
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(nombreArchivo), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Normalizar texto de búsqueda para comparación
                String textoBusquedaLower = (textoBusqueda != null) ? textoBusqueda.toLowerCase() : null;
                boolean pasaFiltro = true;

                // Filtro por palabra clave
                if (pasaFiltro && textoBusquedaLower != null) {
                    if (!linea.toLowerCase().contains(textoBusquedaLower)) {
                        pasaFiltro = false;
                    }
                }

                // Filtro por fecha
                if (pasaFiltro && (fechaDesde != null || fechaHasta != null)) {
                    try {
                        // Extraemos 'AAAA-MM-DD' de la línea
                        String fechaDeLineaStr = linea.substring(0, 10); 
                        LocalDate fechaDeLinea = LocalDate.parse(fechaDeLineaStr);

                        if (fechaDesde != null && fechaDeLinea.isBefore(fechaDesde)) {
                            pasaFiltro = false;
                        }
                        if (fechaHasta != null && fechaDeLinea.isAfter(fechaHasta)) {
                            pasaFiltro = false;
                        }
                    } catch (Exception e) {
                        // Si la línea no tiene un formato de fecha válido o no se puede parsear no pasa el filtro de fecha
                        pasaFiltro = false;
                    }
                }

                // Si la línea pasó todos los filtros, la añadimos al resultado
                if (pasaFiltro) {
                    resultadoFiltrado.append(linea).append("\n");
                }
            }
        } catch (IOException e) {
            return "Error al leer el archivo de registros ('" + nombreArchivo + "'): " + e.getMessage();
        }

        if (resultadoFiltrado.length() == 0) {
            return "No se encontraron registros que coincidan con los filtros aplicados en '" + nombreArchivo + "'.";
        }
        return resultadoFiltrado.toString();
    }

    // Método para leer y devolver el contenido de un archivo de registro
    public String getContenidoRegistro(String tipoRegistro) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy_MM");
        String mesAnio = fechaActualRegistro.format(formato);
        String nombreArchivo = "";

        switch (tipoRegistro) {
            case "Materia Prima":
                nombreArchivo = "Movimientos_materiaPrima_" + mesAnio + ".txt";
                break;
            case "Insumos":
                nombreArchivo = "Movimientos_Insumos_" + mesAnio + ".txt";
                break;
            case "Productos Finales":
                nombreArchivo = "Movimientos_ProductosFinales_" + mesAnio + ".txt";
                break;
            case "Ventas":
                nombreArchivo = "Movimientos_Ventas_" + mesAnio + ".txt";
                break;
            default:
                return "Tipo de registro no válido.";
        }

        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            return "Aún no existen registros para '" + tipoRegistro + "' en el mes actual.";
        }

        StringBuilder contenido = new StringBuilder();
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(archivo, java.nio.charset.StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        } catch (IOException e) {
            return "Error al leer el archivo de registro: " + e.getMessage();
        }

        if (contenido.length() == 0) {
            return "El registro de '" + tipoRegistro + "' para este mes está vacío.";
        }
        
        return contenido.toString();
    }
    /**
     * Devuelve el contenido de un archivo de registro, aplicando filtros opcionales.
     * @param tipoRegistro El tipo de registro a leer (ej. "Ventas").
     * @param textoClave   Palabra clave para buscar en la línea. Si es nulo o vacío, se ignora.
     * @param fechaInicio  Fecha de inicio del rango (formato AAAA-MM-DD). Si es nulo o vacío, se ignora.
     * @param fechaFin     Fecha de fin del rango (formato AAAA-MM-DD). Si es nulo o vacío, se ignora.
     * @return Un string con el contenido filtrado del registro.
     */
    
    public String getContenidoRegistroFiltrado(String tipoRegistro, String textoClave, String fechaInicio, String fechaFin) {
        // Primero, obtenemos el contenido completo del registro
        String contenidoCompleto = getContenidoRegistro(tipoRegistro);
        
        // Si no hay registros o hubo un error, lo devolvemos tal cual
        if (contenidoCompleto.startsWith("Aún no existen registros") || contenidoCompleto.startsWith("Error")) {
            return contenidoCompleto;
        }

        // Preparamos los filtros
        final String textoBusqueda = (textoClave == null || textoClave.trim().isEmpty()) ? null : textoClave.trim().toLowerCase();
        LocalDate fInicio = null;
        LocalDate fFin = null;

        try {
            if (fechaInicio != null && !fechaInicio.trim().isEmpty()) {
                fInicio = LocalDate.parse(fechaInicio.trim());
            }
            if (fechaFin != null && !fechaFin.trim().isEmpty()) {
                fFin = LocalDate.parse(fechaFin.trim());
            }
        } catch (java.time.format.DateTimeParseException e) {
            return "Error: Formato de fecha incorrecto. Use AAAA-MM-DD.";
        }
        
        // Procesamos el contenido línea por línea
        StringBuilder resultadoFiltrado = new StringBuilder();
        String[] lineas = contenidoCompleto.split("\n");

        for (String linea : lineas) {
            if (linea.trim().isEmpty()) continue;

            boolean pasaFiltro = true;

            // Filtro por palabra clave
            if (pasaFiltro && textoBusqueda != null) {
                if (!linea.toLowerCase().contains(textoBusqueda)) {
                    pasaFiltro = false;
                }
            }

            // Filtro por fecha
            if (pasaFiltro && (fInicio != null || fFin != null)) {
                try {
                    String fechaDeLineaStr = linea.substring(0, 10); // Extraemos 'AAAA-MM-DD'
                    LocalDate fechaDeLinea = LocalDate.parse(fechaDeLineaStr);

                    if (fInicio != null && fechaDeLinea.isBefore(fInicio)) {
                        pasaFiltro = false;
                    }
                    if (fFin != null && fechaDeLinea.isAfter(fFin)) {
                        pasaFiltro = false;
                    }
                } catch (Exception e) {
                    // Si la línea no tiene un formato de fecha válido, no pasa el filtro de fecha
                    pasaFiltro = false; 
                }
            }
            
            // Si la línea pasó todos los filtros, la añadimos al resultado
            if (pasaFiltro) {
                resultadoFiltrado.append(linea).append("\n");
            }
        }

        if (resultadoFiltrado.length() == 0) {
            return "No se encontraron registros que coincidan con los filtros aplicados.";
        }

        return resultadoFiltrado.toString();
    }
}
