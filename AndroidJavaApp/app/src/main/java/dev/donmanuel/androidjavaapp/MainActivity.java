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
import dev.donmanuel.androidjavaapp.di.ServiceLocator;
import dev.donmanuel.androidjavaapp.model.Post;
import dev.donmanuel.androidjavaapp.presenter.PostListPresenter;
import dev.donmanuel.androidjavaapp.repository.IPostRepository;

/**
 * Main activity that displays a list of posts.
 * Follows Single Responsibility Principle by focusing only on UI concerns.
 * Follows Dependency Inversion Principle by depending on abstractions.
 * Implements PostListView interface to follow Liskov Substitution Principle.
 */
public class MainActivity extends AppCompatActivity implements PostListPresenter.PostListView {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private TextView errorTextView;
    private static final String TAG = "MainActivity";
    
    private PostListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        errorTextView = findViewById(R.id.errorTextView);

        // Get repository from ServiceLocator (dependency injection)
        IPostRepository postRepository = ServiceLocator.getInstance().getPostRepository();
        
        // Create presenter and load posts
        presenter = new PostListPresenter(postRepository, this);
        presenter.loadPosts();
    }

    @Override
    public void showLoading() {
        errorTextView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorTextView.setText("Loading...");
    }

    @Override
    public void hideLoading() {
        // This will be handled in displayPosts or showError
    }

    @Override
    public void displayPosts(List<Post> posts) {
        Log.d(TAG, "Number of posts received: " + posts.size());
        setupRecyclerView(posts);
        recyclerView.setVisibility(View.VISIBLE);
        errorTextView.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Log.e(TAG, "Error: " + message);
        recyclerView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(message);
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
}