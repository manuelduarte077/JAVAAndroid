package dev.donmanuel.solidandroid.domain.repository;

import dev.donmanuel.solidandroid.domain.model.User;

/**
 * Interfaz que define las operaciones de autenticación (Principio de Segregación de Interfaces)
 */
public interface AuthRepository {
    /**
     * Autentica un usuario con email y contraseña
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @return Usuario autenticado o null si la autenticación falla
     */
    User login(String email, String password);
    
    /**
     * Registra un nuevo usuario
     * @param name Nombre del usuario
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @return Usuario registrado o null si el registro falla
     */
    User register(String name, String email, String password);
    
    /**
     * Cierra la sesión del usuario actual
     */
    void logout();
    
    /**
     * Verifica si hay un usuario con sesión activa
     * @return Usuario con sesión activa o null si no hay sesión
     */
    User getCurrentUser();
}
