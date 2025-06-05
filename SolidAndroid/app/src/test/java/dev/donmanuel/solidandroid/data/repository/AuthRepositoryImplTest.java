package dev.donmanuel.solidandroid.data.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dev.donmanuel.solidandroid.data.local.UserPreferences;
import dev.donmanuel.solidandroid.domain.model.User;

/**
 * Pruebas unitarias para la implementación del repositorio de autenticación
 */
public class AuthRepositoryImplTest {

    @Mock
    private UserPreferences userPreferences;

    private AuthRepositoryImpl authRepository;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        authRepository = new AuthRepositoryImpl(userPreferences);
    }

    @Test
    public void login_validCredentials_returnsAuthenticatedUser() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        String name = "Test User";
        String hashedPassword = hashPassword(password);
        
        when(userPreferences.hasCredentials()).thenReturn(true);
        when(userPreferences.getEmail()).thenReturn(email);
        when(userPreferences.getPassword()).thenReturn(hashedPassword);
        when(userPreferences.getName()).thenReturn(name);

        // Act
        User result = authRepository.login(email, password);

        // Assert
        assertNotNull("El resultado no debería ser null", result);
        assertEquals("El email debería coincidir", email, result.getEmail());
        assertEquals("El nombre debería coincidir", name, result.getName());
        assertTrue("El usuario debería estar autenticado", result.isAuthenticated());
    }

    @Test
    public void login_invalidCredentials_returnsNull() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        String wrongPassword = "wrongpassword";
        String hashedPassword = hashPassword(password);
        
        when(userPreferences.hasCredentials()).thenReturn(true);
        when(userPreferences.getEmail()).thenReturn(email);
        when(userPreferences.getPassword()).thenReturn(hashedPassword);

        // Act
        User result = authRepository.login(email, wrongPassword);

        // Assert
        assertNull("El resultado debería ser null para credenciales inválidas", result);
    }

    @Test
    public void login_noCredentialsStored_returnsNull() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        
        when(userPreferences.hasCredentials()).thenReturn(false);

        // Act
        User result = authRepository.login(email, password);

        // Assert
        assertNull("El resultado debería ser null cuando no hay credenciales almacenadas", result);
    }

    @Test
    public void register_validData_returnsAuthenticatedUser() {
        // Arrange
        String name = "Test User";
        String email = "test@example.com";
        String password = "password123";
        
        doNothing().when(userPreferences).saveUserCredentials(anyString(), anyString(), anyString());

        // Act
        User result = authRepository.register(name, email, password);

        // Assert
        assertNotNull("El resultado no debería ser null", result);
        assertEquals("El email debería coincidir", email, result.getEmail());
        assertEquals("El nombre debería coincidir", name, result.getName());
        assertTrue("El usuario debería estar autenticado", result.isAuthenticated());
        verify(userPreferences, times(1)).saveUserCredentials(email, password, name);
    }

    @Test
    public void logout_callsClearUserCredentials() {
        // Arrange
        doNothing().when(userPreferences).clearUserCredentials();

        // Act
        authRepository.logout();

        // Assert
        verify(userPreferences, times(1)).clearUserCredentials();
    }

    @Test
    public void getCurrentUser_hasCredentials_returnsAuthenticatedUser() {
        // Arrange
        String email = "test@example.com";
        String name = "Test User";
        
        when(userPreferences.hasCredentials()).thenReturn(true);
        when(userPreferences.getEmail()).thenReturn(email);
        when(userPreferences.getName()).thenReturn(name);

        // Act
        User result = authRepository.getCurrentUser();

        // Assert
        assertNotNull("El resultado no debería ser null", result);
        assertEquals("El email debería coincidir", email, result.getEmail());
        assertEquals("El nombre debería coincidir", name, result.getName());
        assertTrue("El usuario debería estar autenticado", result.isAuthenticated());
    }

    @Test
    public void getCurrentUser_noCredentials_returnsNull() {
        // Arrange
        when(userPreferences.hasCredentials()).thenReturn(false);

        // Act
        User result = authRepository.getCurrentUser();

        // Assert
        assertNull("El resultado debería ser null cuando no hay credenciales almacenadas", result);
    }
    
    /**
     * Método auxiliar para simular el hash de contraseñas
     * Nota: Este método debe coincidir con la lógica de hash en AuthRepositoryImpl
     */
    private String hashPassword(String password) {
        // Simulación simple del hash para pruebas
        return password + "_hashed";
    }
}
