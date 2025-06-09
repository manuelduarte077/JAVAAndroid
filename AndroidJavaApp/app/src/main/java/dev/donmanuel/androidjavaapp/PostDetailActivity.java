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
import dev.donmanuel.androidjavaapp.di.ServiceLocator;
import dev.donmanuel.androidjavaapp.model.Comment;
import dev.donmanuel.androidjavaapp.presenter.PostDetailPresenter;
import dev.donmanuel.androidjavaapp.repository.ICommentRepository;

/**
 * Activity that displays the details of a post and its comments.
 * Follows Single Responsibility Principle by focusing only on UI concerns.
 * Follows Dependency Inversion Principle by depending on abstractions.
 * Implements PostDetailView interface to follow Liskov Substitution Principle.
 */
public class PostDetailActivity extends AppCompatActivity implements PostDetailPresenter.PostDetailView {

    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private TextView postTitle, postBody;
    private static final String TAG = "PostDetailActivity";
    
    private PostDetailPresenter presenter;

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
        
        // Get repository from ServiceLocator (dependency injection)
        ICommentRepository commentRepository = ServiceLocator.getInstance().getCommentRepository();
        
        // Create presenter and load comments
        presenter = new PostDetailPresenter(commentRepository, this);
        presenter.loadComments(postId);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void displayComments(List<Comment> comments) {
        commentAdapter = new CommentAdapter(comments);
        recyclerView.setAdapter(commentAdapter);
    }

    @Override
    public void showError(String message) {
        Log.e(TAG, message);
        // Could show an error message to the user here
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