package com.carrillo.movieflix.services;

public interface CallbackListenerRetrofit<T> {

    void onResponse(T resultado);

    void onFailure(Throwable error);
}
