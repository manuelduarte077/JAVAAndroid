package dev.donmanuel.solidpermission.permission;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ReflectionHelpers;

@RunWith(RobolectricTestRunner.class)
public class StoragePermissionStrategyTest {

    @Mock
    private Activity mockActivity;

    private StoragePermissionStrategy storagePermissionStrategy;
    private static final int REQUEST_CODE = 100;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        storagePermissionStrategy = new StoragePermissionStrategy(mockActivity, REQUEST_CODE);
    }

    @Test
    public void getPermissionName_returnsCorrectName() {
        assertEquals("Almacenamiento", storagePermissionStrategy.getPermissionName());
    }

    @Test
    @Config(sdk = Build.VERSION_CODES.Q) // Android 10
    public void checkPermission_onAndroid10_checksReadWriteExternalStorage() {
        // Configurar el mock para simular que los permisos están concedidos
        when(mockActivity.checkSelfPermission(eq(Manifest.permission.READ_EXTERNAL_STORAGE)))
                .thenReturn(PackageManager.PERMISSION_GRANTED);
        when(mockActivity.checkSelfPermission(eq(Manifest.permission.WRITE_EXTERNAL_STORAGE)))
                .thenReturn(PackageManager.PERMISSION_GRANTED);

        // Verificar que checkPermission devuelve true
        assertTrue(storagePermissionStrategy.checkPermission());
        
        // Configurar el mock para simular que un permiso está denegado
        when(mockActivity.checkSelfPermission(eq(Manifest.permission.WRITE_EXTERNAL_STORAGE)))
                .thenReturn(PackageManager.PERMISSION_DENIED);
                
        // Verificar que checkPermission devuelve false
        assertFalse(storagePermissionStrategy.checkPermission());
    }

    @Test
    @Config(sdk = Build.VERSION_CODES.R) // Android 11
    public void checkPermission_onAndroid11_alwaysReturnsTrue() {
        // En Android 11, siempre debe devolver true ya que se usa el gestor de archivos del sistema
        assertTrue(storagePermissionStrategy.checkPermission());
    }

    @Test
    @Config(sdk = Build.VERSION_CODES.TIRAMISU) // Android 13
    public void checkPermission_onAndroid13_checksMediaPermissions() {
        // Configurar el mock para simular que los permisos están concedidos
        when(mockActivity.checkSelfPermission(eq(Manifest.permission.READ_MEDIA_IMAGES)))
                .thenReturn(PackageManager.PERMISSION_GRANTED);
        when(mockActivity.checkSelfPermission(eq(Manifest.permission.READ_MEDIA_VIDEO)))
                .thenReturn(PackageManager.PERMISSION_GRANTED);
        when(mockActivity.checkSelfPermission(eq(Manifest.permission.READ_MEDIA_AUDIO)))
                .thenReturn(PackageManager.PERMISSION_GRANTED);

        // Verificar que checkPermission devuelve true
        assertTrue(storagePermissionStrategy.checkPermission());
        
        // Configurar el mock para simular que un permiso está denegado
        when(mockActivity.checkSelfPermission(eq(Manifest.permission.READ_MEDIA_AUDIO)))
                .thenReturn(PackageManager.PERMISSION_DENIED);
                
        // Verificar que checkPermission devuelve false
        assertFalse(storagePermissionStrategy.checkPermission());
    }

    @Test
    @Config(sdk = Build.VERSION_CODES.Q) // Android 10
    public void requestPermission_onAndroid10_requestsReadWriteExternalStorage() {
        // Ejecutar el método a probar
        storagePermissionStrategy.requestPermission();

        // Verificar que se llamó al método requestPermissions con los permisos correctos
        verify(mockActivity).requestPermissions(
                eq(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }),
                eq(REQUEST_CODE)
        );
    }

    @Test
    @Config(sdk = Build.VERSION_CODES.R) // Android 11
    public void requestPermission_onAndroid11_showsToastAndDoesNotRequestPermissions() {
        // Ejecutar el método a probar con Toast mockeado
        try (MockedStatic<Toast> mockedToast = Mockito.mockStatic(Toast.class)) {
            mockedToast.when(() -> Toast.makeText(any(), anyString(), anyInt())).thenReturn(mock(Toast.class));
            
            storagePermissionStrategy.requestPermission();
            
            // Verificar que se mostró un Toast
            mockedToast.verify(() -> Toast.makeText(any(), anyString(), anyInt()));
            
            // Verificar que NO se llamó al método requestPermissions
            verify(mockActivity, never()).requestPermissions(any(), anyInt());
        }
    }

    @Test
    @Config(sdk = Build.VERSION_CODES.TIRAMISU) // Android 13
    public void requestPermission_onAndroid13_requestsMediaPermissions() {
        // Ejecutar el método a probar
        storagePermissionStrategy.requestPermission();

        // Verificar que se llamó al método requestPermissions con los permisos correctos
        verify(mockActivity).requestPermissions(
                eq(new String[]{
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.READ_MEDIA_VIDEO,
                        Manifest.permission.READ_MEDIA_AUDIO
                }),
                eq(REQUEST_CODE)
        );
    }
}
