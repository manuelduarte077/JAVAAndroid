package dev.donmanuel.solidpermission.permission;

import android.app.Activity;

/**
 * Proveedor de estrategias de permisos (Patrón Factory)
 * Esta clase implementa el principio de Inversión de Dependencias (DIP)
 * permitiendo que las clases de alto nivel no dependan de las implementaciones concretas.
 */
public class PermissionProvider {

    /**
     * Crea una estrategia de permisos según el tipo solicitado
     * @param activity Actividad desde la que se solicitan los permisos
     * @param permissionType Tipo de permiso a crear
     * @param requestCode Código de solicitud para identificar el permiso
     * @return La estrategia de permisos creada
     */
    public static PermissionStrategy createPermissionStrategy(Activity activity, PermissionType permissionType, int requestCode) {
        switch (permissionType) {
            case STORAGE:
                return new StoragePermissionStrategy(activity, requestCode);
            case LOCATION:
                return new LocationPermissionStrategy(activity, requestCode);
            case CAMERA:
                return new CameraPermissionStrategy(activity, requestCode);
            case MICROPHONE:
                return new MicrophonePermissionStrategy(activity, requestCode);
            default:
                throw new IllegalArgumentException("Tipo de permiso no soportado: " + permissionType);
        }
    }

    /**
     * Enumeración de los tipos de permisos disponibles
     */
    public enum PermissionType {
        STORAGE,
        LOCATION,
        CAMERA,
        MICROPHONE
    }
}
