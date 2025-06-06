package dev.donmanuel.solidpermission.permission;

/**
 * Interfaz que define la estrategia para manejar permisos (Patrón Strategy)
 */
public interface PermissionStrategy {
    /**
     * Solicita el permiso al usuario
     */
    void requestPermission();
    
    /**
     * Verifica si el permiso ya ha sido concedido
     * @return true si el permiso está concedido, false en caso contrario
     */
    boolean checkPermission();
    
    /**
     * Obtiene el nombre descriptivo del permiso
     * @return Nombre del permiso
     */
    String getPermissionName();
}
