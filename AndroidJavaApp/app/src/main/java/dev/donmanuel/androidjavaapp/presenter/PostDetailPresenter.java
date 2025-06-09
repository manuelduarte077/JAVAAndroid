package dev.donmanuel.androidjavaapp.presenter;

import java.util.List;

import dev.donmanuel.androidjavaapp.model.Comment;
import dev.donmanuel.androidjavaapp.repository.ICommentRepository;
import dev.donmanuel.androidjavaapp.repository.PostRepositoryCallback;

/**
 * Presenter for the post detail screen.
 * Follows Single Responsibility Principle by handling only presentation logic.
 * Follows Liskov Substitution Principle through the PostDetailView interface.
 */
public class PostDetailPresenter {

    private final ICommentRepository commentRepository;
    private final PostDetailView view;

    public PostDetailPresenter(ICommentRepository commentRepository, PostDetailView view) {
        this.commentRepository = commentRepository;
        this.view = view;
    }

    public void loadComments(int postId) {
        commentRepository.getComments(postId, new PostRepositoryCallback<List<Comment>>() {
            @Override
            public void onSuccess(List<Comment> comments) {
                view.displayComments(comments);
            }

            @Override
            public void onError(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }

    /**
     * Interface for the view that displays post details and comments.
     * Follows Interface Segregation Principle by providing only needed methods.
     */
    public interface PostDetailView {
        void displayComments(List<Comment> comments);
        void showError(String message);
    }
}
