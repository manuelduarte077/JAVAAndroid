package dev.donmanuel.solidpermission.permission;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.app.Activity;
import android.content.pm.PackageManager;
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

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {28})
public class PermissionManagerTest {

    @Mock
    private Activity mockActivity;

    @Mock
    private PermissionManager.PermissionCallback mockCallback;

    @Mock
    private PermissionStrategy mockStrategy;

    private PermissionManager permissionManager;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Crear el gestor de permisos
        permissionManager = new PermissionManager(mockActivity);
        permissionManager.setCallback(mockCallback);
        
        // Acceder al mapa de estrategias a través de reflexión para inyectar nuestro mock
        try {
            java.lang.reflect.Field field = PermissionManager.class.getDeclaredField("permissionStrategies");
            field.setAccessible(true);
            java.util.Map<Integer, PermissionStrategy> strategies = (java.util.Map<Integer, PermissionStrategy>) field.get(permissionManager);
            strategies.put(PermissionManager.CAMERA_PERMISSION_REQUEST_CODE, mockStrategy);
        } catch (Exception e) {
            throw new RuntimeException("Error al acceder al campo privado", e);
        }
        
        // Configurar el comportamiento del mock de estrategia
        when(mockStrategy.getPermissionName()).thenReturn("Test");
    }

    @Test
    public void requestPermission_whenPermissionGranted_notifiesCallback() {
        // Configurar el mock para simular que el permiso está concedido
        when(mockStrategy.checkPermission()).thenReturn(true);
        
        // Ejecutar el método a probar con Toast mockeado
        try (MockedStatic<Toast> mockedToast = Mockito.mockStatic(Toast.class)) {
            mockedToast.when(() -> Toast.makeText(any(), anyString(), anyInt())).thenReturn(mock(Toast.class));
            
            permissionManager.requestPermission(PermissionManager.CAMERA_PERMISSION_REQUEST_CODE);
            
            // Verificar que se llamó al callback con los parámetros correctos
            verify(mockCallback).onPermissionResult(
                    eq(PermissionManager.CAMERA_PERMISSION_REQUEST_CODE),
                    eq(true),
                    anyString()
            );
            
            // Verificar que no se llamó al método requestPermission de la estrategia
            verify(mockStrategy, never()).requestPermission();
        }
    }

    @Test
    public void requestPermission_whenPermissionDenied_requestsPermission() {
        // Configurar el mock para simular que el permiso está denegado
        when(mockStrategy.checkPermission()).thenReturn(false);
        
        // Ejecutar el método a probar
        permissionManager.requestPermission(PermissionManager.CAMERA_PERMISSION_REQUEST_CODE);
        
        // Verificar que se llamó al método requestPermission de la estrategia
        verify(mockStrategy).requestPermission();
        
        // Verificar que se llamó al callback con los parámetros correctos
        verify(mockCallback).onPermissionRequested(
                eq(PermissionManager.CAMERA_PERMISSION_REQUEST_CODE),
                anyString()
        );
    }

    @Test
    public void onRequestPermissionsResult_whenAllGranted_notifiesCallbackWithSuccess() {
        // Configurar los parámetros para la prueba
        int requestCode = PermissionManager.CAMERA_PERMISSION_REQUEST_CODE;
        String[] permissions = new String[]{"android.permission.CAMERA"};
        int[] grantResults = new int[]{PackageManager.PERMISSION_GRANTED};
        
        // Ejecutar el método a probar con Toast mockeado
        try (MockedStatic<Toast> mockedToast = Mockito.mockStatic(Toast.class)) {
            mockedToast.when(() -> Toast.makeText(any(), anyString(), anyInt())).thenReturn(mock(Toast.class));
            
            permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
            
            // Verificar que se llamó al callback con los parámetros correctos
            verify(mockCallback).onPermissionResult(
                    eq(requestCode),
                    eq(true),
                    anyString()
            );
        }
    }

    @Test
    public void onRequestPermissionsResult_whenSomeDenied_notifiesCallbackWithFailure() {
        // Configurar los parámetros para la prueba
        int requestCode = PermissionManager.CAMERA_PERMISSION_REQUEST_CODE;
        String[] permissions = new String[]{"android.permission.CAMERA"};
        int[] grantResults = new int[]{PackageManager.PERMISSION_DENIED};
        
        // Ejecutar el método a probar con Toast mockeado
        try (MockedStatic<Toast> mockedToast = Mockito.mockStatic(Toast.class)) {
            mockedToast.when(() -> Toast.makeText(any(), anyString(), anyInt())).thenReturn(mock(Toast.class));
            
            permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
            
            // Verificar que se llamó al callback con los parámetros correctos
            verify(mockCallback).onPermissionResult(
                    eq(requestCode),
                    eq(false),
                    anyString()
            );
        }
    }

    @Test
    public void onRequestPermissionsResult_whenInvalidRequestCode_doesNothing() {
        // Configurar los parámetros para la prueba con un código de solicitud inválido
        int invalidRequestCode = 999;
        String[] permissions = new String[]{"android.permission.CAMERA"};
        int[] grantResults = new int[]{PackageManager.PERMISSION_GRANTED};
        
        // Ejecutar el método a probar
        permissionManager.onRequestPermissionsResult(invalidRequestCode, permissions, grantResults);
        
        // Verificar que no se llamó al callback
        verify(mockCallback, never()).onPermissionResult(anyInt(), anyBoolean(), anyString());
    }
}
