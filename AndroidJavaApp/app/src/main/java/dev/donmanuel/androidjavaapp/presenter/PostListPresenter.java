package dev.donmanuel.androidjavaapp.presenter;

import java.util.List;

import dev.donmanuel.androidjavaapp.model.Post;
import dev.donmanuel.androidjavaapp.repository.IPostRepository;
import dev.donmanuel.androidjavaapp.repository.PostRepositoryCallback;

/**
 * Presenter for the post list screen.
 * Follows Single Responsibility Principle by handling only presentation logic.
 * Follows Liskov Substitution Principle through the PostListView interface.
 */
public class PostListPresenter {

    private final IPostRepository postRepository;
    private final PostListView view;

    public PostListPresenter(IPostRepository postRepository, PostListView view) {
        this.postRepository = postRepository;
        this.view = view;
    }

    public void loadPosts() {
        view.showLoading();
        
        postRepository.getPosts(new PostRepositoryCallback<List<Post>>() {
            @Override
            public void onSuccess(List<Post> posts) {
                view.hideLoading();
                view.displayPosts(posts);
            }

            @Override
            public void onError(String errorMessage) {
                view.hideLoading();
                view.showError(errorMessage);
            }
        });
    }

    /**
     * Interface for the view that displays posts.
     * Follows Interface Segregation Principle by providing only needed methods.
     */
    public interface PostListView {
        void showLoading();
        void hideLoading();
        void displayPosts(List<Post> posts);
        void showError(String message);
    }
}
