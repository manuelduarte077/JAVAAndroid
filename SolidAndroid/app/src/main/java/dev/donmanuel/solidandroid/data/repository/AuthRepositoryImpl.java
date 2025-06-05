package dev.donmanuel.solidandroid.data.repository;

import dev.donmanuel.solidandroid.data.local.UserPreferences;
import dev.donmanuel.solidandroid.domain.model.User;
import dev.donmanuel.solidandroid.domain.repository.AuthRepository;

/**
 * Implementación concreta del repositorio de autenticación
 * (Principio de Abierto/Cerrado - extensible sin modificar el código existente)
 */
public class AuthRepositoryImpl implements AuthRepository {
    private final UserPreferences userPreferences;

    // Inyección de dependencias a través del constructor
    public AuthRepositoryImpl(UserPreferences userPreferences) {
        this.userPreferences = userPreferences;
    }

    @Override
    public User login(String email, String password) {
        // Verificar si las credenciales coinciden con las almacenadas
        if (userPreferences.hasCredentials() &&
                email.equals(userPreferences.getEmail()) &&
                validatePassword(password, userPreferences.getPassword())) {
            
            User user = new User(userPreferences.getEmail(), userPreferences.getName());
            user.setAuthenticated(true);
            return user;
        }
        return null;
    }

    @Override
    public User register(String name, String email, String password) {
        // Guardar las credenciales en el almacenamiento local
        userPreferences.saveUserCredentials(email, password, name);
        
        // Crear y devolver el usuario registrado
        User user = new User(email, name);
        user.setAuthenticated(true);
        return user;
    }

    @Override
    public void logout() {
        // Limpiar las credenciales almacenadas
        userPreferences.clearUserCredentials();
    }

    @Override
    public User getCurrentUser() {
        // Verificar si hay un usuario con sesión activa
        if (userPreferences.hasCredentials()) {
            User user = new User(userPreferences.getEmail(), userPreferences.getName());
            user.setAuthenticated(true);
            return user;
        }
        return null;
    }

    /**
     * Valida si la contraseña proporcionada coincide con la almacenada
     * @param inputPassword Contraseña ingresada por el usuario
     * @param storedPassword Contraseña almacenada (hasheada)
     * @return true si las contraseñas coinciden, false en caso contrario
     */
    private boolean validatePassword(String inputPassword, String storedPassword) {
        // En una implementación real, aquí se compararía el hash de la contraseña ingresada
        // con la contraseña almacenada
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(inputPassword.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            String hashedInput = android.util.Base64.encodeToString(hash, android.util.Base64.DEFAULT);
            return hashedInput.equals(storedPassword);
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Fallback simple en caso de error
            return (inputPassword + "_hashed").equals(storedPassword);
        }
    }
}
