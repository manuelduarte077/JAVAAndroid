package dev.donmanuel.solidandroid.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Implementación de UserPreferences utilizando SharedPreferences
 * (Principio de Responsabilidad Única - solo maneja el almacenamiento local)
 */
public class SharedPreferencesManager implements UserPreferences {
    private static final String PREF_NAME = "user_auth_prefs";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_PASSWORD = "user_password";
    private static final String KEY_NAME = "user_name";

    private final SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void saveUserCredentials(String email, String password, String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, hashPassword(password));
        editor.putString(KEY_NAME, name);
        editor.apply();
    }

    @Override
    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    @Override
    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }

    @Override
    public String getName() {
        return sharedPreferences.getString(KEY_NAME, null);
    }

    @Override
    public void clearUserCredentials() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_EMAIL);
        editor.remove(KEY_PASSWORD);
        editor.remove(KEY_NAME);
        editor.apply();
    }

    @Override
    public boolean hasCredentials() {
        return getEmail() != null && getPassword() != null;
    }

    /**
     * Método para hashear la contraseña antes de guardarla
     * @param password Contraseña en texto plano
     * @return Contraseña hasheada
     */
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeToString(hash, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Fallback simple en caso de error
            return password + "_hashed";
        }
    }
}
