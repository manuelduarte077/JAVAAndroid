package dev.donmanuel.solidpermission.permission;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que gestiona las diferentes estrategias de permisos
 * Implementa los principios SOLID:
 * - S: Responsabilidad Única - Solo se encarga de gestionar permisos
 * - O: Abierto/Cerrado - Extensible para nuevos tipos de permisos sin modificar código existente
 * - L: Sustitución de Liskov - Las estrategias de permisos son intercambiables
 * - I: Segregación de Interfaces - Interfaces específicas para cada responsabilidad
 * - D: Inversión de Dependencias - Depende de abstracciones, no de implementaciones concretas
 */
public class PermissionManager {
    
    // Constantes para los códigos de solicitud de permisos
    public static final int STORAGE_PERMISSION_REQUEST_CODE = 100;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 102;
    public static final int MICROPHONE_PERMISSION_REQUEST_CODE = 103;
    
    private final Activity activity;
    private final Map<Integer, PermissionStrategy> permissionStrategies;
    private PermissionCallback callback;
    
    /**
     * Constructor del gestor de permisos
     * @param activity Actividad desde la que se solicitan los permisos
     */
    public PermissionManager(Activity activity) {
        this.activity = activity;
        this.permissionStrategies = new HashMap<>();
        initializePermissionStrategies();
    }
    
    /**
     * Inicializa las estrategias de permisos usando el proveedor
     */
    private void initializePermissionStrategies() {
        // Utilizamos el PermissionProvider para crear las estrategias (Inversión de Dependencias)
        permissionStrategies.put(STORAGE_PERMISSION_REQUEST_CODE, 
                PermissionProvider.createPermissionStrategy(
                        activity, 
                        PermissionProvider.PermissionType.STORAGE, 
                        STORAGE_PERMISSION_REQUEST_CODE));
        
        permissionStrategies.put(LOCATION_PERMISSION_REQUEST_CODE, 
                PermissionProvider.createPermissionStrategy(
                        activity, 
                        PermissionProvider.PermissionType.LOCATION, 
                        LOCATION_PERMISSION_REQUEST_CODE));
        
        permissionStrategies.put(CAMERA_PERMISSION_REQUEST_CODE, 
                PermissionProvider.createPermissionStrategy(
                        activity, 
                        PermissionProvider.PermissionType.CAMERA, 
                        CAMERA_PERMISSION_REQUEST_CODE));
        
        permissionStrategies.put(MICROPHONE_PERMISSION_REQUEST_CODE, 
                PermissionProvider.createPermissionStrategy(
                        activity, 
                        PermissionProvider.PermissionType.MICROPHONE, 
                        MICROPHONE_PERMISSION_REQUEST_CODE));
    }
    
    /**
     * Establece el callback para notificar sobre el resultado de los permisos
     * @param callback Callback a establecer
     * @return La instancia actual del gestor de permisos
     */
    public PermissionManager setCallback(PermissionCallback callback) {
        this.callback = callback;
        return this;
    }
    
    /**
     * Maneja la solicitud de un permiso específico
     * @param requestCode Código del permiso a solicitar
     */
    public void requestPermission(int requestCode) {
        PermissionStrategy strategy = permissionStrategies.get(requestCode);
        if (strategy != null) {
            if (strategy.checkPermission()) {
                String message = "Ya tienes permiso de " + strategy.getPermissionName();
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                if (callback != null) {
                    callback.onPermissionResult(requestCode, true, message);
                }
            } else {
                strategy.requestPermission();
                if (callback != null) {
                    callback.onPermissionRequested(requestCode, strategy.getPermissionName());
                }
            }
        }
    }
    
    /**
     * Procesa el resultado de la solicitud de permisos
     * @param requestCode Código de la solicitud
     * @param permissions Permisos solicitados
     * @param grantResults Resultados de la solicitud
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionStrategy strategy = permissionStrategies.get(requestCode);
        if (strategy == null) return;
        
        boolean allGranted = true;
        for (int result : grantResults) {
            if (result != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
        }
        
        String message;
        if (allGranted) {
            message = "Permiso de " + strategy.getPermissionName() + " concedido";
        } else {
            message = "Permiso de " + strategy.getPermissionName() + " denegado";
        }
        
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        
        if (callback != null) {
            callback.onPermissionResult(requestCode, allGranted, message);
        }
    }
    
    /**
     * Interfaz para notificar sobre el resultado de los permisos
     */
    public interface PermissionCallback {
        /**
         * Llamado cuando se solicita un permiso
         * @param requestCode Código del permiso solicitado
         * @param permissionName Nombre del permiso solicitado
         */
        void onPermissionRequested(int requestCode, String permissionName);
        
        /**
         * Llamado cuando se obtiene el resultado de la solicitud de un permiso
         * @param requestCode Código del permiso solicitado
         * @param isGranted true si el permiso fue concedido, false en caso contrario
         * @param message Mensaje descriptivo del resultado
         */
        void onPermissionResult(int requestCode, boolean isGranted, String message);
    }
}
