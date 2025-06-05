package dev.donmanuel.solidandroid.domain.usecase;

import dev.donmanuel.solidandroid.domain.model.User;
import dev.donmanuel.solidandroid.domain.repository.AuthRepository;

/**
 * Caso de uso para obtener el usuario actual
 * (Principio de Responsabilidad Única - solo maneja la lógica de obtener el usuario actual)
 */
public class GetCurrentUserUseCase {
    private final AuthRepository authRepository;

    public GetCurrentUserUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    /**
     * Ejecuta el caso de uso para obtener el usuario actual
     * @return Usuario actual o null si no hay sesión activa
     */
    public User execute() {
        return authRepository.getCurrentUser();
    }
}
