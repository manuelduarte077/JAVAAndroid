package dev.donmanuel.solidandroid.domain.usecase;

import dev.donmanuel.solidandroid.domain.model.User;
import dev.donmanuel.solidandroid.domain.repository.AuthRepository;

/**
 * Caso de uso para el registro de usuarios
 * (Principio de Responsabilidad Única - solo maneja la lógica de registro)
 */
public class RegisterUseCase {
    private final AuthRepository authRepository;

    public RegisterUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    /**
     * Ejecuta el caso de uso de registro
     * @param name Nombre del usuario
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @return Usuario registrado o null si el registro falla
     */
    public User execute(String name, String email, String password) {
        return authRepository.register(name, email, password);
    }
}
