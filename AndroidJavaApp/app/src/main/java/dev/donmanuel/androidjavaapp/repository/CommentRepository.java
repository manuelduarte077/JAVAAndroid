package dev.donmanuel.androidjavaapp.repository;

import java.util.List;

import dev.donmanuel.androidjavaapp.model.Comment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import dev.donmanuel.androidjavaapp.api.ApiService;

/**
 * Repository class that handles Comment data operations.
 * Follows Single Responsibility Principle by focusing only on Comment data handling.
 */
public class CommentRepository implements ICommentRepository {
    
    private final ApiService apiService;
    
    public CommentRepository(ApiService apiService) {
        this.apiService = apiService;
    }
    
    @Override
    public void getComments(int postId, PostRepositoryCallback<List<Comment>> callback) {
        apiService.getComments(postId).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error: Failed to load comments. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                callback.onError("Failed to fetch comments: " + t.getMessage());
            }
        });
    }
}
