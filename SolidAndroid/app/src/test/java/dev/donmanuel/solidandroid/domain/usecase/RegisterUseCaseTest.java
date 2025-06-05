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
 * Pruebas unitarias para el caso de uso RegisterUseCase
 */
public class RegisterUseCaseTest {

    @Mock
    private AuthRepository authRepository;

    private RegisterUseCase registerUseCase;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        registerUseCase = new RegisterUseCase(authRepository);
    }

    @Test
    public void execute_validRegistration_returnsAuthenticatedUser() {
        // Arrange
        String name = "Test User";
        String email = "test@example.com";
        String password = "password123";
        User expectedUser = new User(email, name);
        expectedUser.setAuthenticated(true);
        
        when(authRepository.register(name, email, password)).thenReturn(expectedUser);

        // Act
        User result = registerUseCase.execute(name, email, password);

        // Assert
        assertNotNull("El resultado no debería ser null", result);
        assertEquals("El email debería coincidir", email, result.getEmail());
        assertEquals("El nombre debería coincidir", name, result.getName());
        assertTrue("El usuario debería estar autenticado", result.isAuthenticated());
    }

    @Test
    public void execute_registrationError_returnsNull() {
        // Arrange
        String name = "Test User";
        String email = "test@example.com";
        String password = "password123";
        
        when(authRepository.register(anyString(), anyString(), anyString())).thenReturn(null);

        // Act
        User result = registerUseCase.execute(name, email, password);

        // Assert
        assertNull("El resultado debería ser null cuando hay un error en el registro", result);
    }
}
