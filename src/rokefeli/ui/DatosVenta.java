package rokefeli.ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatosVenta extends JDialog {

    private JComboBox<String> cbxProductos;
    private JTextField txtCantidad;
    private JTextField txtComprador;
    private boolean confirmado = false;

    public DatosVenta(Frame parent, String[] productosDisponibles) {
        super(parent, "Registrar Venta", true);

        if (productosDisponibles == null || productosDisponibles.length == 0) {
            JOptionPane.showMessageDialog(parent, "No hay productos con stock disponibles para la venta.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Fila 1: Producto
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Producto a Vender:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cbxProductos = new JComboBox<>(productosDisponibles);
        add(cbxProductos, gbc);

        // Fila 2: Cantidad
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Cantidad:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtCantidad = new JTextField();
        add(txtCantidad, gbc);
        
        // Fila 3: Comprador
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Nombre del Comprador:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtComprador = new JTextField();
        add(txtComprador, gbc);

        // Fila 4: Fecha
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Fecha de Venta:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField txtFecha = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        txtFecha.setEditable(false);
        add(txtFecha, gbc);

        // Fila 5: Botones
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnVender = new JButton("Vender");
        btnVender.addActionListener(e -> {
            if (validarCampos()) {
                confirmado = true;
                dispose();
            }
        });
        panelBotones.add(btnVender);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnCancelar);
        add(panelBotones, gbc);

        pack();
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    private boolean validarCampos() {
        if (txtComprador.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del comprador no puede estar vacío.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser un número positivo.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // Getters para que la interfaz principal pueda recuperar los datos
    public boolean isConfirmado() { return confirmado; }
    public String getProductoSeleccionado() { return (String) cbxProductos.getSelectedItem(); }
    public int getCantidad() { return Integer.parseInt(txtCantidad.getText().trim()); }
    public String getComprador() { return txtComprador.getText().trim(); }
}