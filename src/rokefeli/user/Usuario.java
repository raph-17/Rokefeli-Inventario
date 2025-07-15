package rokefeli.user;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Base64; // Para codificar el hash a String

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L; // Para control de versiones de serialización
    private String nombreUsuario;
    private String contrasenaHash; // Almacenaremos el hash de la contraseña

    public Usuario(String nombreUsuario, String contrasenaPlana) {
        this.nombreUsuario = nombreUsuario;
        // La contraseña se hashea al crear el usuario
        this.contrasenaHash = hashContrasena(contrasenaPlana);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    /**
     * Hashea la contraseña plana usando SHA-256.
     * Para mayor seguridad, en un entorno real se usarían salts aleatorios y algoritmos como BCrypt/PBKDF2.
     */
    public static String hashContrasena(String contrasenaPlana) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(contrasenaPlana.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash); // Convierte bytes a String para almacenar
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error al hashear la contraseña: " + e.getMessage());
            // En un entorno de producción, manejar esto de forma más robusta.
            return null;
        }
    }

    /**
     * Verifica si una contraseña plana coincide con el hash almacenado.
     */
    public boolean verificarContrasena(String contrasenaPlana) {
        return this.contrasenaHash.equals(hashContrasena(contrasenaPlana));
    }
}