package dev.donmanuel.androidjavaapp.repository;

/**
 * Generic callback interface for repository operations.
 * Follows Interface Segregation Principle by providing a focused interface.
 * @param <T> The type of data returned on success
 */
public interface PostRepositoryCallback<T> {
    void onSuccess(T data);
    void onError(String errorMessage);
}
