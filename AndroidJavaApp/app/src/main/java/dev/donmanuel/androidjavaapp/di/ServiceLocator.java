package dev.donmanuel.androidjavaapp.di;

import dev.donmanuel.androidjavaapp.api.ApiService;
import dev.donmanuel.androidjavaapp.api.NetworkModule;
import dev.donmanuel.androidjavaapp.repository.CommentRepository;
import dev.donmanuel.androidjavaapp.repository.ICommentRepository;
import dev.donmanuel.androidjavaapp.repository.IPostRepository;
import dev.donmanuel.androidjavaapp.repository.PostRepository;

/**
 * Simple dependency injection container.
 * Follows Dependency Inversion Principle by providing dependencies.
 */
public class ServiceLocator {
    
    private static ServiceLocator instance;
    
    private final NetworkModule networkModule;
    private IPostRepository postRepository;
    private ICommentRepository commentRepository;
    
    private ServiceLocator() {
        networkModule = new NetworkModule();
    }
    
    public static synchronized ServiceLocator getInstance() {
        if (instance == null) {
            instance = new ServiceLocator();
        }
        return instance;
    }
    
    public IPostRepository getPostRepository() {
        if (postRepository == null) {
            ApiService apiService = networkModule.getApiService();
            postRepository = new PostRepository(apiService);
        }
        return postRepository;
    }
    
    public ICommentRepository getCommentRepository() {
        if (commentRepository == null) {
            ApiService apiService = networkModule.getApiService();
            commentRepository = new CommentRepository(apiService);
        }
        return commentRepository;
    }
    
    // For testing purposes
    public void setPostRepository(IPostRepository repository) {
        this.postRepository = repository;
    }
    
    public void setCommentRepository(ICommentRepository repository) {
        this.commentRepository = repository;
    }
}
