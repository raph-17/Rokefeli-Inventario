package rokefeli.ui;

import javax.swing.*;
import java.awt.*;

public class DatosProductoFinal extends JDialog {

    private JComboBox<String> cbxLotes;
    private JComboBox<String> cbxTipoProducto;
    private JTextField txtCantidad;
    private boolean confirmado = false;

    public DatosProductoFinal(Frame parent, String[] idsLotes) {
        super(parent, "Crear Producto Final", true);

        if (idsLotes == null || idsLotes.length == 0) {
            JOptionPane.showMessageDialog(parent,
                    "No hay lotes de miel en estado 'Lista para Envasar'.\n" +
                            "Por favor, transforme un lote primero.",
                    "Acción Requerida",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Fila 1: Lote de Miel
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Lote de Miel a usar:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cbxLotes = new JComboBox<>(idsLotes);
        add(cbxLotes, gbc);
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;

        // Fila 2: Tipo de Producto
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Tipo de Producto a crear:"), gbc);

        String[] tiposProducto = {
                "Frasco 1kg (vidrio)",
                "Frasco 1/2kg (vidrio)",
                "Frasco 1kg (plástico)",
                "Frasco 1/2kg (plástico)",
                "Bolsa 1kg",
                "Bolsa 1/2kg"
        };
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cbxTipoProducto = new JComboBox<>(tiposProducto);
        add(cbxTipoProducto, gbc);
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;

        // Fila 3: Cantidad
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Cantidad de unidades a crear:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtCantidad = new JTextField();
        add(txtCantidad, gbc);

        // Fila 4: Botones
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton btnCrear = new JButton("Crear");
        btnCrear.addActionListener(e -> {
            if (validarCampos()) {
                confirmado = true;
                dispose();
            }
        });
        buttonPanel.add(btnCrear);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        buttonPanel.add(btnCancelar);

        add(buttonPanel, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        pack();
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private boolean validarCampos() {
        String cantTexto = txtCantidad.getText();
        if (cantTexto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Cantidad' no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            int cantidad = Integer.parseInt(cantTexto);
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido en 'Cantidad'.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public String getIdLoteSeleccionado() {
        return (String) cbxLotes.getSelectedItem();
    }

    public String getTipoProductoSeleccionado() {
        return (String) cbxTipoProducto.getSelectedItem();
    }

    public int getCantidad() {
        return Integer.parseInt(txtCantidad.getText());
    }

    public boolean isConfirmado() {
        return confirmado;
    }
}