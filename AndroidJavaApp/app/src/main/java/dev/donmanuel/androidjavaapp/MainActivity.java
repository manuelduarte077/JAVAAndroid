package dev.donmanuel.androidjavaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.donmanuel.androidjavaapp.adapter.PostAdapter;
import dev.donmanuel.androidjavaapp.api.ApiClient;
import dev.donmanuel.androidjavaapp.api.ApiService;
import dev.donmanuel.androidjavaapp.model.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private TextView errorTextView;  // Para mostrar errores o mensajes de carga
    private static final String TAG = "MainActivity";  // Para identificar los logs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        errorTextView = findViewById(R.id.errorTextView);  // El TextView para errores

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        fetchPostsFromApi(apiService);
    }

    private void fetchPostsFromApi(ApiService apiService) {
        Log.d(TAG, "fetchPostsFromApi called");

        errorTextView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorTextView.setText("Loading...");

        Call<List<Post>> call = apiService.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Post> postList = response.body();
                    Log.d(TAG, "Number of posts received: " + postList.size());
                    setupRecyclerView(postList);
                    recyclerView.setVisibility(View.VISIBLE);  // Mostramos el RecyclerView
                    errorTextView.setVisibility(View.GONE);    // Ocultamos el mensaje de error
                } else {
                    Log.e(TAG, "Response not successful or body is null. Code: " + response.code());
                    showError("Error: Failed to load posts. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                showError("Failed to fetch posts: " + t.getMessage());
            }
        });
    }

    private void setupRecyclerView(List<Post> postList) {
        postAdapter = new PostAdapter(postList, post -> {
            Intent intent = new Intent(MainActivity.this, PostDetailActivity.class);
            intent.putExtra("postId", post.getId());
            intent.putExtra("postTitle", post.getTitle());
            intent.putExtra("postBody", post.getBody());

            startActivity(intent);
        });
        recyclerView.setAdapter(postAdapter);
    }

    private void showError(String message) {
        recyclerView.setVisibility(View.GONE);   // Ocultamos el RecyclerView
        errorTextView.setVisibility(View.VISIBLE);  // Mostramos el TextView de error
        errorTextView.setText(message);             // Mostramos el mensaje de error
    }
}