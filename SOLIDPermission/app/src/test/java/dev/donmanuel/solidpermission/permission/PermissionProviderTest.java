package dev.donmanuel.solidpermission.permission;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.app.Activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {28})
public class PermissionProviderTest {

    @Mock
    private Activity mockActivity;

    private static final int REQUEST_CODE = 100;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createPermissionStrategy_withStorageType_returnsStorageStrategy() {
        // Ejecutar el método a probar
        PermissionStrategy strategy = PermissionProvider.createPermissionStrategy(
                mockActivity,
                PermissionProvider.PermissionType.STORAGE,
                REQUEST_CODE
        );

        // Verificar que se devolvió una estrategia del tipo correcto
        assertNotNull(strategy);
        assertTrue(strategy instanceof StoragePermissionStrategy);
    }

    @Test
    public void createPermissionStrategy_withLocationType_returnsLocationStrategy() {
        // Ejecutar el método a probar
        PermissionStrategy strategy = PermissionProvider.createPermissionStrategy(
                mockActivity,
                PermissionProvider.PermissionType.LOCATION,
                REQUEST_CODE
        );

        // Verificar que se devolvió una estrategia del tipo correcto
        assertNotNull(strategy);
        assertTrue(strategy instanceof LocationPermissionStrategy);
    }

    @Test
    public void createPermissionStrategy_withCameraType_returnsCameraStrategy() {
        // Ejecutar el método a probar
        PermissionStrategy strategy = PermissionProvider.createPermissionStrategy(
                mockActivity,
                PermissionProvider.PermissionType.CAMERA,
                REQUEST_CODE
        );

        // Verificar que se devolvió una estrategia del tipo correcto
        assertNotNull(strategy);
        assertTrue(strategy instanceof CameraPermissionStrategy);
    }

    @Test
    public void createPermissionStrategy_withMicrophoneType_returnsMicrophoneStrategy() {
        // Ejecutar el método a probar
        PermissionStrategy strategy = PermissionProvider.createPermissionStrategy(
                mockActivity,
                PermissionProvider.PermissionType.MICROPHONE,
                REQUEST_CODE
        );

        // Verificar que se devolvió una estrategia del tipo correcto
        assertNotNull(strategy);
        assertTrue(strategy instanceof MicrophonePermissionStrategy);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createPermissionStrategy_withNullType_throwsException() {
        // Ejecutar el método a probar con un tipo nulo debería lanzar una excepción
        PermissionProvider.createPermissionStrategy(
                mockActivity,
                null,
                REQUEST_CODE
        );
    }
}
