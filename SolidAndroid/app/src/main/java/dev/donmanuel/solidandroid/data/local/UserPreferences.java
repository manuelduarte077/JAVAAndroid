package dev.donmanuel.solidandroid.data.local;

/**
 * Interfaz para el almacenamiento de preferencias del usuario (Principio de Responsabilidad Única)
 */
public interface UserPreferences {
    /**
     * Guarda las credenciales del usuario
     * @param email Email del usuario
     * @param password Contraseña encriptada del usuario
     * @param name Nombre del usuario
     */
    void saveUserCredentials(String email, String password, String name);
    
    /**
     * Obtiene el email guardado
     * @return Email del usuario o null si no existe
     */
    String getEmail();
    
    /**
     * Obtiene la contraseña guardada
     * @return Contraseña encriptada del usuario o null si no existe
     */
    String getPassword();
    
    /**
     * Obtiene el nombre guardado
     * @return Nombre del usuario o null si no existe
     */
    String getName();
    
    /**
     * Elimina las credenciales guardadas
     */
    void clearUserCredentials();
    
    /**
     * Verifica si hay credenciales guardadas
     * @return true si hay credenciales guardadas, false en caso contrario
     */
    boolean hasCredentials();
}
