package dev.donmanuel.androidjavaapp.repository;

import java.util.List;
import dev.donmanuel.androidjavaapp.model.Comment;

/**
 * Interface for Comment repository operations.
 * Follows Dependency Inversion Principle by allowing high-level modules to depend on abstractions.
 */
public interface ICommentRepository {
    void getComments(int postId, PostRepositoryCallback<List<Comment>> callback);
}
