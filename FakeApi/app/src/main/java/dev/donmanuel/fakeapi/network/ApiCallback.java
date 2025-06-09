package dev.donmanuel.fakeapi.network;

public interface ApiCallback<T> {
    void onSuccess(T result);

    void onError(Exception e);
}
