package rokefeli.ui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import rokefeli.logic.GestorInventario;
import rokefeli.model.LoteMielCosecha;
import rokefeli.model.ProductoFinal;

public class InterfazRokefeli extends javax.swing.JFrame {

    GestorInventario gestor = new GestorInventario();
    
    public InterfazRokefeli() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        btnBuscarMateriaPrima = new javax.swing.JButton();
        btnMostrarMateriaPrimaTotal = new javax.swing.JButton();
        btnIngresarMateriaPrima = new javax.swing.JButton();
        btnTransformarMateriaPrima = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtaResultadosMateriaPrima = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtaResultadosMateriaPrima2 = new javax.swing.JTextArea();
        btnIngresarInsumo = new javax.swing.JButton();
        btnMostrarInsumosTotal = new javax.swing.JButton();
        btnBuscarInsumo = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtaResultadosMateriaPrima1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnBuscarMateriaPrima.setText("Búsqueda");
        btnBuscarMateriaPrima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarMateriaPrimaActionPerformed(evt);
            }
        });

        btnMostrarMateriaPrimaTotal.setText("Mostrar Materia Prima Total");
        btnMostrarMateriaPrimaTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarMateriaPrimaTotalActionPerformed(evt);
            }
        });

        btnIngresarMateriaPrima.setText("Ingresar Materia Prima");
        btnIngresarMateriaPrima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarMateriaPrimaActionPerformed(evt);
            }
        });

        btnTransformarMateriaPrima.setText("Transformar");
        btnTransformarMateriaPrima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransformarMateriaPrimaActionPerformed(evt);
            }
        });

        txtaResultadosMateriaPrima.setEditable(false);
        txtaResultadosMateriaPrima.setColumns(20);
        txtaResultadosMateriaPrima.setRows(5);
        jScrollPane1.setViewportView(txtaResultadosMateriaPrima);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(btnIngresarMateriaPrima)
                .addGap(18, 18, 18)
                .addComponent(btnMostrarMateriaPrimaTotal)
                .addGap(18, 18, 18)
                .addComponent(btnBuscarMateriaPrima)
                .addGap(18, 18, 18)
                .addComponent(btnTransformarMateriaPrima)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(138, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIngresarMateriaPrima)
                    .addComponent(btnMostrarMateriaPrimaTotal)
                    .addComponent(btnBuscarMateriaPrima)
                    .addComponent(btnTransformarMateriaPrima))
                .addGap(38, 38, 38)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Materia Prima", jPanel1);

        txtaResultadosMateriaPrima2.setColumns(20);
        txtaResultadosMateriaPrima2.setRows(5);
        jScrollPane3.setViewportView(txtaResultadosMateriaPrima2);

        btnIngresarInsumo.setText("Ingresar Insumo");
        btnIngresarInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarInsumoActionPerformed(evt);
            }
        });

        btnMostrarInsumosTotal.setText("Mostrar Insumos Totales");

        btnBuscarInsumo.setText("Búsqueda");
        btnBuscarInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarInsumoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(btnIngresarInsumo)
                .addGap(18, 18, 18)
                .addComponent(btnMostrarInsumosTotal)
                .addGap(18, 18, 18)
                .addComponent(btnBuscarInsumo)
                .addContainerGap(176, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(113, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIngresarInsumo)
                    .addComponent(btnMostrarInsumosTotal)
                    .addComponent(btnBuscarInsumo))
                .addGap(61, 61, 61)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Insumos", jPanel2);

        txtaResultadosMateriaPrima1.setColumns(20);
        txtaResultadosMateriaPrima1.setRows(5);
        jScrollPane2.setViewportView(txtaResultadosMateriaPrima1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(199, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Productos Finales", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresarMateriaPrimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarMateriaPrimaActionPerformed
        LoteMielCosecha nuevoLote = gestor.ingresarLoteMiel(this);
        
        if(nuevoLote != null){
            if(!gestor.repetirLote(nuevoLote.getIdLote())){
                gestor.inventarioLotes.add(nuevoLote);
                JOptionPane.showMessageDialog(rootPane, "Se ha agregado correctamente el lote nuevo.");
                String tipoMov = "ENT_LOTE_MIEL";
                String unidad = "KG";
                String descripcion = "Origen: " + nuevoLote.getOrigen() + ", Floración: " + nuevoLote.getFloracion() + ", Estado: " + nuevoLote.getEstado();
                gestor.registrarMovimientoMateriaPrima(tipoMov, nuevoLote.getIdLote(), nuevoLote.getCantKg(), unidad, descripcion);
                txtaResultadosMateriaPrima.setText(gestor.mostrarLotes());
            } else{
                JOptionPane.showMessageDialog(rootPane, "El id de lote ingresado ya existe.");
            }
        } else{
            JOptionPane.showMessageDialog(rootPane, "Ingreso de Lote de Miel cancelado.");
        }
        
    }//GEN-LAST:event_btnIngresarMateriaPrimaActionPerformed

    private void btnMostrarMateriaPrimaTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarMateriaPrimaTotalActionPerformed
        txtaResultadosMateriaPrima.setText(gestor.mostrarLotes());
    }//GEN-LAST:event_btnMostrarMateriaPrimaTotalActionPerformed

    private void btnBuscarMateriaPrimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarMateriaPrimaActionPerformed
        String criterio = JOptionPane.showInputDialog(this, "Ingrese ID del Lote o Floración para buscar:");
        if (criterio == null || criterio.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Búsqueda cancelada o entrada vacía.");
            return;
        }

        txtaResultadosMateriaPrima.setText(gestor.buscarMateriaPrima(criterio));
    }//GEN-LAST:event_btnBuscarMateriaPrimaActionPerformed

    private void btnTransformarMateriaPrimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTransformarMateriaPrimaActionPerformed
        // Validaciones
        String idLote = JOptionPane.showInputDialog(this, "Ingrese el ID del Lote a transformar:");
        if (idLote == null || idLote.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Transformación cancelada o ID vacío.");
            return;
        }

        LoteMielCosecha loteSeleccionado = null;
        for (LoteMielCosecha lote : gestor.inventarioLotes) {
            if (lote.getIdLote().equalsIgnoreCase(idLote)) {
                loteSeleccionado = lote;
                break;
            }
        }

        if (loteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Lote no encontrado.");
            return;
        }
        
        String estadoActual = loteSeleccionado.getEstado();

        // Crear un JComboBox con los estados
        String[] estados = {"En Reposo", "Pasteurizada", "Lista para Envasar"};
        JComboBox<String> comboEstados = new JComboBox<>(estados);
        comboEstados.setSelectedItem(estadoActual); // Estado actual seleccionado por defecto

        JPanel panel = new JPanel();
        panel.add(new JLabel("Seleccione el nuevo estado:"));
        panel.add(comboEstados);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Cambiar Estado", JOptionPane.OK_CANCEL_OPTION);
        if (resultado != JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(this, "Cambio de estado cancelado.");
            return;
        }

        String nuevoEstado = (String) comboEstados.getSelectedItem();
        if (nuevoEstado.equals(estadoActual)) {
            JOptionPane.showMessageDialog(this, "El lote ya está en el estado: " + nuevoEstado);
            return;
        }
        
        // Validar transiciones de estado válidas
        if (nuevoEstado.equals("En Reposo")) {
            JOptionPane.showMessageDialog(this, "ERROR, no se puede revertir a 'En Reposo'");
            return;
        } else if (nuevoEstado.equals("Pasteurizada")) {
            if (!estadoActual.equals("En Reposo")) {
                JOptionPane.showMessageDialog(this, "ERROR, el lote ya ha sido pasteurizado.");
            return;
            }
        } else if (nuevoEstado.equals("Lista para Envasar")) {
            if (!estadoActual.equals("Pasteurizada")) {
                JOptionPane.showMessageDialog(this, "ERROR, sólo un lote pausterizado puede pasar a 'Listo para Envasar'.");
            return;
            }
        }
        
        // Actualizar el estado del lote
        gestor.transformarLote(loteSeleccionado.getIdLote(), nuevoEstado);
        JOptionPane.showMessageDialog(this, "Estado del lote actualizado a: " + nuevoEstado);
        
        // Registrar movimiento en el archivo txt
        String tipoMov = "TRA_LOTE_MIEL";
                String idItem = loteSeleccionado.getIdLote();
                double cantidad = loteSeleccionado.getCantKg();
                String unidad = "KG";
                String descripcion = "Origen: " + loteSeleccionado.getOrigen() + ", Floración: " + loteSeleccionado.getFloracion() + ", Estado: " + loteSeleccionado.getEstado();
                gestor.registrarMovimientoMateriaPrima(tipoMov, idItem, cantidad, unidad, descripcion);
        
        // Mostrar el cambio en la interfaz de usuario
        txtaResultadosMateriaPrima.setText(gestor.buscarMateriaPrima(loteSeleccionado.getIdLote()));
        
    }//GEN-LAST:event_btnTransformarMateriaPrimaActionPerformed

    private void btnIngresarInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarInsumoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnIngresarInsumoActionPerformed

    private void btnBuscarInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarInsumoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarInsumoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfazRokefeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazRokefeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazRokefeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazRokefeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfazRokefeli().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarInsumo;
    private javax.swing.JButton btnBuscarMateriaPrima;
    private javax.swing.JButton btnIngresarInsumo;
    private javax.swing.JButton btnIngresarMateriaPrima;
    private javax.swing.JButton btnMostrarInsumosTotal;
    private javax.swing.JButton btnMostrarMateriaPrimaTotal;
    private javax.swing.JButton btnTransformarMateriaPrima;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea txtaResultadosMateriaPrima;
    private javax.swing.JTextArea txtaResultadosMateriaPrima1;
    private javax.swing.JTextArea txtaResultadosMateriaPrima2;
    // End of variables declaration//GEN-END:variables
}
