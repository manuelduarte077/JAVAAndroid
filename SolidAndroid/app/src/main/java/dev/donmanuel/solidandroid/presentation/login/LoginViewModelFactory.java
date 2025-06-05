package dev.donmanuel.solidandroid.presentation.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dev.donmanuel.solidandroid.domain.usecase.GetCurrentUserUseCase;
import dev.donmanuel.solidandroid.domain.usecase.LoginUseCase;
import dev.donmanuel.solidandroid.domain.usecase.RegisterUseCase;
import dev.donmanuel.solidandroid.util.EmailValidator;

/**
 * Factory para crear instancias de LoginViewModel con sus dependencias
 * (Principio de Inversión de Dependencias - permite inyectar las dependencias)
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final EmailValidator emailValidator;

    public LoginViewModelFactory(
            LoginUseCase loginUseCase,
            RegisterUseCase registerUseCase,
            GetCurrentUserUseCase getCurrentUserUseCase,
            EmailValidator emailValidator) {
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.emailValidator = emailValidator;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(
                    loginUseCase,
                    registerUseCase,
                    getCurrentUserUseCase,
                    emailValidator
            );
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
