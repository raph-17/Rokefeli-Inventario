package rokefeli.logic;

import rokefeli.auth.Usuario;
import java.io.*;

public class GestorAutenticacion {
    private static final String USUARIO_FILE = "usuario_autenticacion.ser"; // Archivo binario
    private Usuario usuarioRegistrado;

    public GestorAutenticacion() {
        cargarUsuario();
    }

    // Carga el usuario desde el archivo serializado
    private void cargarUsuario() {
        File file = new File(USUARIO_FILE);
        if (file.exists() && file.length() > 0) { // Verifica si el archivo existe y no está vacío
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USUARIO_FILE))) {
                usuarioRegistrado = (Usuario) ois.readObject();
                System.out.println("DEBUG: Usuario cargado desde archivo.");
            } catch (FileNotFoundException e) {
                System.err.println("Error: Archivo de usuario no encontrado: " + e.getMessage());
                usuarioRegistrado = null; // No se encontró usuario
            } catch (IOException e) {
                System.err.println("Error de I/O al cargar usuario: " + e.getMessage());
                usuarioRegistrado = null;
            } catch (ClassNotFoundException e) {
                System.err.println("Error: Clase de usuario no encontrada al deserializar: " + e.getMessage());
                usuarioRegistrado = null;
            }
        } else {
            System.out.println("DEBUG: Archivo de usuario no existe o está vacío. Se creará uno nuevo si se registra.");
            usuarioRegistrado = null; // No hay usuario registrado aún
        }
    }

    // Guarda el usuario en el archivo serializado
    public void guardarUsuario(Usuario usuario) {
        this.usuarioRegistrado = usuario; // Actualiza el usuario en memoria
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USUARIO_FILE))) {
            oos.writeObject(usuario);
            System.out.println("DEBUG: Usuario guardado en archivo.");
        } catch (IOException e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
        }
    }

    /**
     * Valida las credenciales ingresadas.
     * Si no hay usuario registrado, registra el primero que se intente loguear.
     * @param usuarioIngresado Nombre de usuario intentado.
     * @param contrasenaIngresada Contraseña plana intentada.
     * @return true si las credenciales son válidas, false de lo contrario.
     */
    public boolean validarCredenciales(String usuarioIngresado, String contrasenaIngresada) {
        if (usuarioRegistrado == null) {
            // Si no hay usuario registrado, el primer intento se convierte en el usuario por defecto.
            // En un entorno real, esto debería ser un proceso de registro explícito de administrador.
            usuarioRegistrado = new Usuario(usuarioIngresado, contrasenaIngresada);
            guardarUsuario(usuarioRegistrado); // Guarda el nuevo usuario
            System.out.println("DEBUG: Primer usuario registrado como: " + usuarioIngresado);
            return true; // El primer login siempre es exitoso si no hay usuario
        } else {
            // Si ya hay un usuario, verificar las credenciales
            if (usuarioRegistrado.getNombreUsuario().equals(usuarioIngresado) &&
                usuarioRegistrado.verificarContrasena(contrasenaIngresada)) {
                return true;
            }
        }
        return false;
    }

    // Método para obtener el usuario registrado (útil para propósitos de administración o depuración)
    public Usuario getUsuarioRegistrado() {
        return usuarioRegistrado;
    }
}