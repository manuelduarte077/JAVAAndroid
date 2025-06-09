package dev.donmanuel.androidjavaapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Network module that provides API clients.
 * Follows Open/Closed Principle by allowing extension without modification.
 */
public class NetworkModule {
    
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    
    private final Retrofit retrofit;
    
    public NetworkModule() {
        this(BASE_URL);
    }
    
    // Constructor allows for extension with different base URLs
    public NetworkModule(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    
    public <T> T createService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
    
    public ApiService getApiService() {
        return createService(ApiService.class);
    }
}
