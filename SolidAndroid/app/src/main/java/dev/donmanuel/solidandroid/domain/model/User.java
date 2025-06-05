package dev.donmanuel.solidandroid.domain.model;

/**
 * Modelo de usuario que representa la información de un usuario autenticado
 */
public class User {
    private String email;
    private String name;
    private boolean isAuthenticated;

    public User(String email, String name) {
        this.email = email;
        this.name = name;
        this.isAuthenticated = false;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }
}
