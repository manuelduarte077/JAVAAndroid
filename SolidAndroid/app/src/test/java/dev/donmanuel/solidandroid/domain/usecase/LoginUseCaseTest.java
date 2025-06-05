package dev.donmanuel.solidandroid.domain.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dev.donmanuel.solidandroid.domain.model.User;
import dev.donmanuel.solidandroid.domain.repository.AuthRepository;

/**
 * Pruebas unitarias para el caso de uso LoginUseCase
 */
public class LoginUseCaseTest {

    @Mock
    private AuthRepository authRepository;

    private LoginUseCase loginUseCase;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        loginUseCase = new LoginUseCase(authRepository);
    }

    @Test
    public void execute_validCredentials_returnsAuthenticatedUser() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        User expectedUser = new User(email, "Test User");
        expectedUser.setAuthenticated(true);
        
        when(authRepository.login(email, password)).thenReturn(expectedUser);

        // Act
        User result = loginUseCase.execute(email, password);

        // Assert
        assertNotNull("El resultado no debería ser null", result);
        assertEquals("El email debería coincidir", email, result.getEmail());
        assertTrue("El usuario debería estar autenticado", result.isAuthenticated());
    }

    @Test
    public void execute_invalidCredentials_returnsNull() {
        // Arrange
        String email = "test@example.com";
        String password = "wrongpassword";
        
        when(authRepository.login(email, password)).thenReturn(null);

        // Act
        User result = loginUseCase.execute(email, password);

        // Assert
        assertNull("El resultado debería ser null para credenciales inválidas", result);
    }

    @Test
    public void execute_repositoryError_returnsNull() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        
        when(authRepository.login(anyString(), anyString())).thenReturn(null);

        // Act
        User result = loginUseCase.execute(email, password);

        // Assert
        assertNull("El resultado debería ser null cuando hay un error en el repositorio", result);
    }
}
