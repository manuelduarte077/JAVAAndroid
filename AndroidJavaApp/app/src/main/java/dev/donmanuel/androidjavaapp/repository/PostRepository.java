package dev.donmanuel.androidjavaapp.repository;

import java.util.List;

import dev.donmanuel.androidjavaapp.model.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import dev.donmanuel.androidjavaapp.api.ApiService;

/**
 * Repository class that handles Post data operations.
 * Follows Single Responsibility Principle by focusing only on Post data handling.
 */
public class PostRepository implements IPostRepository {
    
    private final ApiService apiService;
    
    public PostRepository(ApiService apiService) {
        this.apiService = apiService;
    }
    
    @Override
    public void getPosts(PostRepositoryCallback<List<Post>> callback) {
        apiService.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error: Failed to load posts. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                callback.onError("Failed to fetch posts: " + t.getMessage());
            }
        });
    }
}
