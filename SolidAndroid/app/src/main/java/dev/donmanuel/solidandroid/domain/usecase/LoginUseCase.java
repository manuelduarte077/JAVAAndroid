package dev.donmanuel.solidandroid.domain.usecase;

import dev.donmanuel.solidandroid.domain.model.User;
import dev.donmanuel.solidandroid.domain.repository.AuthRepository;

/**
 * Caso de uso para la autenticación de usuarios
 * (Principio de Responsabilidad Única - solo maneja la lógica de login)
 */
public class LoginUseCase {
    private final AuthRepository authRepository;

    public LoginUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    /**
     * Ejecuta el caso de uso de login
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @return Usuario autenticado o null si la autenticación falla
     */
    public User execute(String email, String password) {
        return authRepository.login(email, password);
    }
}
