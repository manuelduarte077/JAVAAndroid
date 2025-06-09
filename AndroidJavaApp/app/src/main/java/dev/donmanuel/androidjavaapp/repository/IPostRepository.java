package dev.donmanuel.androidjavaapp.repository;

import java.util.List;
import dev.donmanuel.androidjavaapp.model.Post;

/**
 * Interface for Post repository operations.
 * Follows Dependency Inversion Principle by allowing high-level modules to depend on abstractions.
 */
public interface IPostRepository {
    void getPosts(PostRepositoryCallback<List<Post>> callback);
}
