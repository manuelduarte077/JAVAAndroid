package dev.donmanuel.solidpermission.permission;

import android.Manifest;
import android.app.Activity;

/**
 * Estrategia para manejar permisos de ubicación
 */
public class LocationPermissionStrategy extends BasePermissionStrategy {
    
    /**
     * Constructor para la estrategia de permisos de ubicación
     * @param activity Actividad desde la que se solicitan los permisos
     * @param requestCode Código de solicitud para identificar el permiso
     */
    public LocationPermissionStrategy(Activity activity, int requestCode) {
        super(activity, requestCode);
    }
    
    @Override
    public void requestPermission() {
        requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }

    @Override
    public boolean checkPermission() {
        return checkPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }

    @Override
    public String getPermissionName() {
        return "Ubicación";
    }
}
