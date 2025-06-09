package dev.donmanuel.fakeapi;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import dev.donmanuel.fakeapi.adapters.ImageCarouselAdapter;
import dev.donmanuel.fakeapi.models.Product;
import dev.donmanuel.fakeapi.network.ApiCallback;
import dev.donmanuel.fakeapi.network.ApiClient;

import com.google.android.material.appbar.MaterialToolbar;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView image;
    private TextView title, price, description;
    private RecyclerView imageRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        image = findViewById(R.id.image);
        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
        imageRecyclerView = findViewById(R.id.imageRecyclerView);

        // Configurar el RecyclerView para el carrusel
        imageRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        // Agregar PagerSnapHelper para efecto de paginación
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(imageRecyclerView);

        int productId = getIntent().getIntExtra("productId", 0);
        if (productId == 0) {
            Toast.makeText(this, "Producto no válido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        fetchProductDetails(productId);
    }

    private void fetchProductDetails(int productId) {
        ApiClient apiClient = ApiClient.getInstance();

        apiClient.fetchProductById(productId, new ApiCallback<>() {
            @Override
            public void onSuccess(Product product) {
                runOnUiThread(() -> {
                    title.setText(product.getTitle());
                    price.setText("$" + product.getPrice());
                    description.setText(product.getDescription());
                    Picasso.get().load(product.getImages().get(0)).into(image);

                    // Configurar el adaptador del carrusel de imágenes
                    ImageCarouselAdapter carouselAdapter = new ImageCarouselAdapter(product.getImages());
                    imageRecyclerView.setAdapter(carouselAdapter);
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(() -> Toast.makeText(ProductDetailActivity.this,
                        "Error al cargar detalles: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show());
            }
        });
    }
}