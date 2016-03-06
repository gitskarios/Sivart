package com.alorma.travis.ui.presenter.utils.retrofit;

import android.util.Log;
import java.util.Locale;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public abstract class RetrofitWrapper {

  private String accept;
  private String contentType;
  private String token;
  private String url;

  public RetrofitWrapper accept(String accept) {
    this.accept = accept;
    return this;
  }

  public RetrofitWrapper contentType(String contentType) {
    this.contentType = contentType;
    return this;
  }

  public RetrofitWrapper authorization(String token) {
    this.token = token;
    return this;
  }

  public RetrofitWrapper url(String url) {
    this.url = url;
    return this;
  }

  public Retrofit build() {
    return buildRetrofit(accept, contentType, token, url);
  }

  protected HttpLoggingInterceptor.Logger logger() {
    return message -> Log.i("RETROFIT-TRAVIS", message);
  }

  protected abstract Retrofit buildRetrofit(String accept, String contentType, String token, String url);
}
