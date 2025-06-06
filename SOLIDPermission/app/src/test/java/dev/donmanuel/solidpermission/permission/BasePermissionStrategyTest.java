package dev.donmanuel.solidpermission.permission;

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
public class BasePermissionStrategyTest {

    @Mock
    private Activity mockActivity;

    private static final int REQUEST_CODE = 100;
    private static final String TEST_PERMISSION = Manifest.permission.CAMERA;

    private TestPermissionStrategy permissionStrategy;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        permissionStrategy = new TestPermissionStrategy(mockActivity, REQUEST_CODE);
    }

    @Test
    public void checkPermissions_whenPermissionGranted_returnsTrue() {
        // Configurar el mock para simular que el permiso está concedido
        when(mockActivity.checkSelfPermission(eq(TEST_PERMISSION)))
                .thenReturn(PackageManager.PERMISSION_GRANTED);

        // Verificar que checkPermissions devuelve true
        assertTrue(permissionStrategy.checkPermission());
    }

    @Test
    public void checkPermissions_whenPermissionDenied_returnsFalse() {
        // Configurar el mock para simular que el permiso está denegado
        when(mockActivity.checkSelfPermission(eq(TEST_PERMISSION)))
                .thenReturn(PackageManager.PERMISSION_DENIED);

        // Verificar que checkPermissions devuelve false
        assertFalse(permissionStrategy.checkPermission());
    }

    @Test
    public void requestPermission_callsActivityRequestPermissions() {
        // Ejecutar el método a probar
        permissionStrategy.requestPermission();

        // Verificar que se llamó al método requestPermissions de la actividad
        verify(mockActivity).requestPermissions(
                eq(new String[]{TEST_PERMISSION}),
                eq(REQUEST_CODE)
        );
    }

    /**
     * Implementación concreta de BasePermissionStrategy para pruebas
     */
    private static class TestPermissionStrategy extends BasePermissionStrategy {
        
        public TestPermissionStrategy(Activity activity, int requestCode) {
            super(activity, requestCode);
        }

        @Override
        public void requestPermission() {
            requestPermissions(new String[]{TEST_PERMISSION});
        }

        @Override
        public boolean checkPermission() {
            return checkPermissions(new String[]{TEST_PERMISSION});
        }

        @Override
        public String getPermissionName() {
            return "Test";
        }
    }
}
