package dev.donmanuel.solidandroid.presentation.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dev.donmanuel.solidandroid.domain.model.User;
import dev.donmanuel.solidandroid.domain.usecase.GetCurrentUserUseCase;
import dev.donmanuel.solidandroid.domain.usecase.LoginUseCase;
import dev.donmanuel.solidandroid.domain.usecase.RegisterUseCase;
import dev.donmanuel.solidandroid.util.EmailValidator;

/**
 * ViewModel para la pantalla de login
 * (Principio de Responsabilidad Única - solo maneja la lógica de presentación para el login)
 */
public class LoginViewModel extends ViewModel {

    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final EmailValidator emailValidator;

    private final MutableLiveData<LoginState> loginState = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<User> currentUser = new MutableLiveData<>();

    public LoginViewModel(
            LoginUseCase loginUseCase,
            RegisterUseCase registerUseCase,
            GetCurrentUserUseCase getCurrentUserUseCase,
            EmailValidator emailValidator) {
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.emailValidator = emailValidator;
        
        // Verificar si hay un usuario con sesión activa
        checkCurrentUser();
    }

    /**
     * Verifica si hay un usuario con sesión activa
     */
    public void checkCurrentUser() {
        User user = getCurrentUserUseCase.execute();
        if (user != null && user.isAuthenticated()) {
            currentUser.setValue(user);
            loginState.setValue(LoginState.SUCCESS);
        } else {
            loginState.setValue(LoginState.IDLE);
        }
    }

    /**
     * Inicia sesión con email y contraseña
     * @param email Email del usuario
     * @param password Contraseña del usuario
     */
    public void login(String email, String password) {
        // Validar campos
        if (!validateFields(email, password, null)) {
            return;
        }

        isLoading.setValue(true);
        
        // Simular operación asíncrona
        new Thread(() -> {
            try {
                Thread.sleep(500); // Simular retraso de red
                
                User user = loginUseCase.execute(email, password);
                
                if (user != null && user.isAuthenticated()) {
                    currentUser.postValue(user);
                    loginState.postValue(LoginState.SUCCESS);
                } else {
                    errorMessage.postValue("Credenciales inválidas");
                    loginState.postValue(LoginState.ERROR);
                }
            } catch (Exception e) {
                errorMessage.postValue("Error al iniciar sesión: " + e.getMessage());
                loginState.postValue(LoginState.ERROR);
            } finally {
                isLoading.postValue(false);
            }
        }).start();
    }

    /**
     * Registra un nuevo usuario
     * @param name Nombre del usuario
     * @param email Email del usuario
     * @param password Contraseña del usuario
     */
    public void register(String name, String email, String password) {
        // Validar campos
        if (!validateFields(email, password, name)) {
            return;
        }

        isLoading.setValue(true);
        
        // Simular operación asíncrona
        new Thread(() -> {
            try {
                Thread.sleep(500); // Simular retraso de red
                
                User user = registerUseCase.execute(name, email, password);
                
                if (user != null) {
                    currentUser.postValue(user);
                    loginState.postValue(LoginState.SUCCESS);
                } else {
                    errorMessage.postValue("Error al registrar usuario");
                    loginState.postValue(LoginState.ERROR);
                }
            } catch (Exception e) {
                errorMessage.postValue("Error al registrar: " + e.getMessage());
                loginState.postValue(LoginState.ERROR);
            } finally {
                isLoading.postValue(false);
            }
        }).start();
    }

    /**
     * Valida los campos de entrada
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @param name Nombre del usuario (puede ser null en caso de login)
     * @return true si los campos son válidos, false en caso contrario
     */
    private boolean validateFields(String email, String password, String name) {
        if (name != null && name.trim().isEmpty()) {
            errorMessage.setValue("El nombre es requerido");
            loginState.setValue(LoginState.ERROR);
            return false;
        }
        
        if (email == null || email.trim().isEmpty()) {
            errorMessage.setValue("El email es requerido");
            loginState.setValue(LoginState.ERROR);
            return false;
        }
        
        if (!emailValidator.isValid(email)) {
            errorMessage.setValue("Email inválido");
            loginState.setValue(LoginState.ERROR);
            return false;
        }
        
        if (password == null || password.length() < 6) {
            errorMessage.setValue("La contraseña debe tener al menos 6 caracteres");
            loginState.setValue(LoginState.ERROR);
            return false;
        }
        
        return true;
    }

    // Getters para los LiveData
    public LiveData<LoginState> getLoginState() {
        return loginState;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    /**
     * Estados posibles del proceso de login
     */
    public enum LoginState {
        IDLE,       // Estado inicial
        LOADING,    // Procesando login/registro
        SUCCESS,    // Login/registro exitoso
        ERROR       // Error en login/registro
    }
}
