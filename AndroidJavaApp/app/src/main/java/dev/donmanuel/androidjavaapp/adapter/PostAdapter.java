package dev.donmanuel.androidjavaapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.donmanuel.androidjavaapp.R;
import dev.donmanuel.androidjavaapp.model.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private final List<Post> postList;
    private OnItemClickListener onItemClickListener;

    // Constructor que recibe la lista de Posts
    public PostAdapter(List<Post> postList, OnItemClickListener listener) {
        this.postList = postList;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el layout de cada item del RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        // Obtenemos el post actual según la posición
        Post post = postList.get(position);
        // Establecemos los datos del post en los TextViews correspondientes
        holder.title.setText(post.getTitle());
        holder.body.setText(post.getBody());

        // Manejar el clic en el ítem
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(post);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Devolvemos el tamaño de la lista de Posts
        return postList.size();
    }

    // Clase interna que representa cada elemento del RecyclerView
    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView title, body;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            // Enlazamos los views del layout item_post.xml
            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);
        }
    }

    // Interfaz para manejar los clicks en los items del RecyclerView
    public interface OnItemClickListener {
        void onItemClick(Post post);
    }
}