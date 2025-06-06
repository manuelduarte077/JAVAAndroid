package dev.donmanuel.solidpermission.permission;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.widget.Toast;

/**
 * Estrategia para manejar permisos de almacenamiento
 */
public class StoragePermissionStrategy extends BasePermissionStrategy {
    
    /**
     * Constructor para la estrategia de permisos de almacenamiento
     * @param activity Actividad desde la que se solicitan los permisos
     * @param requestCode Código de solicitud para identificar el permiso
     */
    public StoragePermissionStrategy(Activity activity, int requestCode) {
        super(activity, requestCode);
    }
    
    @Override
    public void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13 y superior
            requestPermissions(new String[]{
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_AUDIO
            });
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11 y 12
            Toast.makeText(activity, "En Android 11+, las aplicaciones usan el gestor de archivos del sistema", 
                    Toast.LENGTH_LONG).show();
        } else {
            // Android 10 y anteriores
            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            });
        }
    }

    @Override
    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return checkPermissions(new String[]{
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_AUDIO
            });
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Para Android 11 y 12, consideramos que siempre tiene acceso a través del gestor de archivos
            return true;
        } else {
            return checkPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            });
        }
    }

    @Override
    public String getPermissionName() {
        return "Almacenamiento";
    }
}
