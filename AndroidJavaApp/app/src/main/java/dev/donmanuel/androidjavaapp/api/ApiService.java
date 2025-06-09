package dev.donmanuel.androidjavaapp.api;

import java.util.List;

import dev.donmanuel.androidjavaapp.model.Comment;
import dev.donmanuel.androidjavaapp.model.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts/{postId}/comments")
    Call<List<Comment>> getComments(@Path("postId") int postId);
}
