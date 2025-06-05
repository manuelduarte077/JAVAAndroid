package dev.donmanuel.solidandroid.domain.usecase;

import dev.donmanuel.solidandroid.domain.repository.AuthRepository;

/**
 * Caso de uso para cerrar sesión
 * (Principio de Responsabilidad Única - solo maneja la lógica de cierre de sesión)
 */
public class LogoutUseCase {
    private final AuthRepository authRepository;

    public LogoutUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    /**
     * Ejecuta el caso de uso de cierre de sesión
     */
    public void execute() {
        authRepository.logout();
    }
}
