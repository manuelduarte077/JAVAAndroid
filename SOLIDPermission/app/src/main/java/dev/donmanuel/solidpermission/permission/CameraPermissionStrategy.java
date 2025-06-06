package dev.donmanuel.solidpermission.permission;

import android.Manifest;
import android.app.Activity;

/**
 * Estrategia para manejar permisos de cámara
 */
public class CameraPermissionStrategy extends BasePermissionStrategy {
    
    /**
     * Constructor para la estrategia de permisos de cámara
     * @param activity Actividad desde la que se solicitan los permisos
     * @param requestCode Código de solicitud para identificar el permiso
     */
    public CameraPermissionStrategy(Activity activity, int requestCode) {
        super(activity, requestCode);
    }
    
    @Override
    public void requestPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA});
    }

    @Override
    public boolean checkPermission() {
        return checkPermissions(new String[]{Manifest.permission.CAMERA});
    }

    @Override
    public String getPermissionName() {
        return "Cámara";
    }
}
