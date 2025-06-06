package dev.donmanuel.solidpermission.permission;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Clase base abstracta para implementaciones de estrategias de permisos
 */
public abstract class BasePermissionStrategy implements PermissionStrategy {
    
    protected final Activity activity;
    protected final int requestCode;
    
    /**
     * Constructor base para estrategias de permisos
     * @param activity Actividad desde la que se solicitan los permisos
     * @param requestCode Código de solicitud para identificar el permiso
     */
    public BasePermissionStrategy(Activity activity, int requestCode) {
        this.activity = activity;
        this.requestCode = requestCode;
    }
    
    /**
     * Verifica si un conjunto de permisos ha sido concedido
     * @param permissions Array de permisos a verificar
     * @return true si todos los permisos están concedidos, false en caso contrario
     */
    protected boolean checkPermissions(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Solicita un conjunto de permisos al usuario
     * @param permissions Array de permisos a solicitar
     */
    protected void requestPermissions(String[] permissions) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }
}
