package dev.donmanuel.solidandroid.presentation.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dev.donmanuel.solidandroid.domain.model.User;
import dev.donmanuel.solidandroid.domain.usecase.GetCurrentUserUseCase;
import dev.donmanuel.solidandroid.domain.usecase.LoginUseCase;
import dev.donmanuel.solidandroid.domain.usecase.RegisterUseCase;
import dev.donmanuel.solidandroid.util.EmailValidator;

/**
 * Pruebas unitarias para el LoginViewModel
 */
public class LoginViewModelTest {

    // Regla necesaria para probar LiveData
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private LoginUseCase loginUseCase;

    @Mock
    private RegisterUseCase registerUseCase;

    @Mock
    private GetCurrentUserUseCase getCurrentUserUseCase;

    @Mock
    private EmailValidator emailValidator;

    @Mock
    private Observer<LoginViewModel.LoginState> loginStateObserver;

    @Mock
    private Observer<String> errorMessageObserver;

    @Mock
    private Observer<Boolean> isLoadingObserver;

    @Mock
    private Observer<User> currentUserObserver;

    private LoginViewModel viewModel;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        viewModel = new LoginViewModel(loginUseCase, registerUseCase, getCurrentUserUseCase, emailValidator);
        
        // Observar LiveData
        viewModel.getLoginState().observeForever(loginStateObserver);
        viewModel.getErrorMessage().observeForever(errorMessageObserver);
        viewModel.getIsLoading().observeForever(isLoadingObserver);
        viewModel.getCurrentUser().observeForever(currentUserObserver);
    }

    @Test
    public void checkCurrentUser_userExists_setsSuccessState() {
        // Arrange
        User user = new User("test@example.com", "Test User");
        user.setAuthenticated(true);
        when(getCurrentUserUseCase.execute()).thenReturn(user);

        // Act
        viewModel.checkCurrentUser();

        // Assert
        assertEquals(LoginViewModel.LoginState.SUCCESS, viewModel.getLoginState().getValue());
        assertEquals(user, viewModel.getCurrentUser().getValue());
    }

    @Test
    public void checkCurrentUser_noUser_setsIdleState() {
        // Arrange
        when(getCurrentUserUseCase.execute()).thenReturn(null);

        // Act
        viewModel.checkCurrentUser();

        // Assert
        assertEquals(LoginViewModel.LoginState.IDLE, viewModel.getLoginState().getValue());
    }

    @Test
    public void login_validCredentials_setsSuccessState() throws InterruptedException {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        User user = new User(email, "Test User");
        user.setAuthenticated(true);
        
        when(emailValidator.isValid(email)).thenReturn(true);
        when(loginUseCase.execute(email, password)).thenReturn(user);

        // Act
        viewModel.login(email, password);
        
        // Esperar a que termine la operación asíncrona simulada
        Thread.sleep(600);

        // Assert
        assertEquals(LoginViewModel.LoginState.SUCCESS, viewModel.getLoginState().getValue());
        assertEquals(user, viewModel.getCurrentUser().getValue());
        assertFalse(viewModel.getIsLoading().getValue());
    }

    @Test
    public void login_invalidEmail_setsErrorState() {
        // Arrange
        String email = "invalid-email";
        String password = "password123";
        
        when(emailValidator.isValid(email)).thenReturn(false);

        // Act
        viewModel.login(email, password);

        // Assert
        assertEquals(LoginViewModel.LoginState.ERROR, viewModel.getLoginState().getValue());
        assertNotNull(viewModel.getErrorMessage().getValue());
    }

    @Test
    public void login_emptyEmail_setsErrorState() {
        // Arrange
        String email = "";
        String password = "password123";

        // Act
        viewModel.login(email, password);

        // Assert
        assertEquals(LoginViewModel.LoginState.ERROR, viewModel.getLoginState().getValue());
        assertNotNull(viewModel.getErrorMessage().getValue());
    }

    @Test
    public void login_shortPassword_setsErrorState() {
        // Arrange
        String email = "test@example.com";
        String password = "12345"; // Menos de 6 caracteres
        
        when(emailValidator.isValid(email)).thenReturn(true);

        // Act
        viewModel.login(email, password);

        // Assert
        assertEquals(LoginViewModel.LoginState.ERROR, viewModel.getLoginState().getValue());
        assertNotNull(viewModel.getErrorMessage().getValue());
    }

    @Test
    public void register_validData_setsSuccessState() throws InterruptedException {
        // Arrange
        String name = "Test User";
        String email = "test@example.com";
        String password = "password123";
        User user = new User(email, name);
        user.setAuthenticated(true);
        
        when(emailValidator.isValid(email)).thenReturn(true);
        when(registerUseCase.execute(name, email, password)).thenReturn(user);

        // Act
        viewModel.register(name, email, password);
        
        // Esperar a que termine la operación asíncrona simulada
        Thread.sleep(600);

        // Assert
        assertEquals(LoginViewModel.LoginState.SUCCESS, viewModel.getLoginState().getValue());
        assertEquals(user, viewModel.getCurrentUser().getValue());
        assertFalse(viewModel.getIsLoading().getValue());
    }

    @Test
    public void register_emptyName_setsErrorState() {
        // Arrange
        String name = "";
        String email = "test@example.com";
        String password = "password123";

        // Act
        viewModel.register(name, email, password);

        // Assert
        assertEquals(LoginViewModel.LoginState.ERROR, viewModel.getLoginState().getValue());
        assertNotNull(viewModel.getErrorMessage().getValue());
    }
}
