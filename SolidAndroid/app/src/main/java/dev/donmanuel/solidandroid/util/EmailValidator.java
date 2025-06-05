package dev.donmanuel.solidandroid.util;

import android.util.Patterns;

/**
 * Clase utilitaria para validar direcciones de email
 * (Principio de Responsabilidad Única - solo maneja la validación de email)
 */
public class EmailValidator {
    
    /**
     * Valida si una cadena es un email válido
     * @param email Cadena a validar
     * @return true si es un email válido, false en caso contrario
     */
    public boolean isValid(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
