package dev.donmanuel.androidjavaapp;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.donmanuel.androidjavaapp.adapter.CommentAdapter;
import dev.donmanuel.androidjavaapp.api.ApiClient;
import dev.donmanuel.androidjavaapp.api.ApiService;
import dev.donmanuel.androidjavaapp.model.Comment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private TextView postTitle, postBody;
    private static final String TAG = "PostDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        recyclerView = findViewById(R.id.recyclerViewComments);
        postTitle = findViewById(R.id.postTitle);
        postBody = findViewById(R.id.postBody);

        int postId = getIntent().getIntExtra("postId", -1);
        String postTitleText = getIntent().getStringExtra("postTitle");
        String postBodyText = getIntent().getStringExtra("postBody");

        // Mostrar título y cuerpo del post
        postTitle.setText(postTitleText);
        postBody.setText(postBodyText);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchCommentsForPost(postId);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void fetchCommentsForPost(int postId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Comment>> call = apiService.getComments(postId);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Comment> comments = response.body();
                    commentAdapter = new CommentAdapter(comments);
                    recyclerView.setAdapter(commentAdapter);
                } else {
                    Log.e(TAG, "Failed to load comments. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e(TAG, "Error fetching comments: " + t.getMessage(), t);
            }
        });
    }

    // Manejar el botón de "Atrás" en el AppBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}