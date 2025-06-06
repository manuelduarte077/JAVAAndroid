package dev.donmanuel.solidpermission.permission;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {28})
public class CameraPermissionStrategyTest {

    @Mock
    private Activity mockActivity;

    private static final int REQUEST_CODE = 102;
    private CameraPermissionStrategy cameraPermissionStrategy;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cameraPermissionStrategy = new CameraPermissionStrategy(mockActivity, REQUEST_CODE);
    }

    @Test
    public void getPermissionName_returnsCorrectName() {
        assertEquals("Cámara", cameraPermissionStrategy.getPermissionName());
    }

    @Test
    public void checkPermission_whenCameraPermissionGranted_returnsTrue() {
        // Configurar el mock para simular que el permiso está concedido
        when(mockActivity.checkSelfPermission(eq(Manifest.permission.CAMERA)))
                .thenReturn(PackageManager.PERMISSION_GRANTED);

        // Verificar que checkPermission devuelve true
        assertTrue(cameraPermissionStrategy.checkPermission());
    }

    @Test
    public void checkPermission_whenCameraPermissionDenied_returnsFalse() {
        // Configurar el mock para simular que el permiso está denegado
        when(mockActivity.checkSelfPermission(eq(Manifest.permission.CAMERA)))
                .thenReturn(PackageManager.PERMISSION_DENIED);

        // Verificar que checkPermission devuelve false
        assertFalse(cameraPermissionStrategy.checkPermission());
    }

    @Test
    public void requestPermission_requestsCameraPermission() {
        // Ejecutar el método a probar
        cameraPermissionStrategy.requestPermission();

        // Verificar que se llamó al método requestPermissions con el permiso de cámara
        verify(mockActivity).requestPermissions(
                eq(new String[]{Manifest.permission.CAMERA}),
                eq(REQUEST_CODE)
        );
    }
}
