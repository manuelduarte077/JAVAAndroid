package dev.donmanuel.solidpermission.permission;

import android.Manifest;
import android.app.Activity;

/**
 * Estrategia para manejar permisos de micrófono
 */
public class MicrophonePermissionStrategy extends BasePermissionStrategy {
    
    /**
     * Constructor para la estrategia de permisos de micrófono
     * @param activity Actividad desde la que se solicitan los permisos
     * @param requestCode Código de solicitud para identificar el permiso
     */
    public MicrophonePermissionStrategy(Activity activity, int requestCode) {
        super(activity, requestCode);
    }
    
    @Override
    public void requestPermission() {
        requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO});
    }

    @Override
    public boolean checkPermission() {
        return checkPermissions(new String[]{Manifest.permission.RECORD_AUDIO});
    }

    @Override
    public String getPermissionName() {
        return "Micrófono";
    }
}
