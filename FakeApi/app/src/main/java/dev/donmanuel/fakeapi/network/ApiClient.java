package dev.donmanuel.fakeapi.network;

import dev.donmanuel.fakeapi.models.Product;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApiClient {
    private static final String TAG = ApiClient.class.getSimpleName();
    private static final String BASE_URL = "https://api.escuelajs.co/api/v1";
    private static ApiClient instance;
    private final OkHttpClient client;
    private final Gson gson;
    private final ExecutorService executorService;

    private ApiClient() {
        client = new OkHttpClient();
        gson = new Gson();
        executorService = Executors.newFixedThreadPool(4);
    }

    public static synchronized ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    public void fetchProducts(ApiCallback<List<Product>> callback) {
        String url = BASE_URL + "/products";
        executeRequest(url, new TypeToken<List<Product>>() {
        }.getType(), callback);
    }

    public void fetchProductById(int productId, ApiCallback<Product> callback) {
        String url = BASE_URL + "/products/" + productId;
        executeRequest(url, Product.class, callback);
    }

    private <T> void executeRequest(String url, Type type, ApiCallback<T> callback) {
        Request request = new Request.Builder().url(url).build();

        executorService.execute(() -> {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    T result = gson.fromJson(responseBody, type);
                    callback.onSuccess(result);
                } else {
                    String errorMessage = "Error: " + response.code();
                    Log.e(TAG, errorMessage);
                    callback.onError(new Exception(errorMessage));
                }
            } catch (Exception e) {
                Log.e(TAG, "Request failed", e);
                callback.onError(e);
            }
        });
    }
}