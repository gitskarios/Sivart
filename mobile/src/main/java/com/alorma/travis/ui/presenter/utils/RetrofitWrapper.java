package com.alorma.travis.ui.presenter.utils;

import android.util.Log;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitWrapper {

  private String acceptParam;
  private String contentType;
  private String accept;

  public RetrofitWrapper() {

  }

  public Retrofit getRetrofit(String url) {
    return createRetrofit(url);
  }

  public Retrofit getRetrofit(String url, String token) {
    return createRetrofit(url, token);
  }

  protected Retrofit createRetrofit(String url) {
    Retrofit.Builder builder = new Retrofit.Builder();
    builder.baseUrl(() -> HttpUrl.parse(url));
    builder.addConverterFactory(GsonConverterFactory.create());
    builder.client(createHttpClient(null));

    return builder.build();
  }

  protected Retrofit createRetrofit(String url, String token) {
    Retrofit.Builder builder = new Retrofit.Builder();
    builder.baseUrl(() -> HttpUrl.parse(url));
    builder.addConverterFactory(GsonConverterFactory.create());
    builder.client(createHttpClient(token));

    return builder.build();
  }

  protected OkHttpClient createHttpClient(String token) {
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    httpClient.addInterceptor(chain -> {
      Request original = chain.request();

      Request request = getRequest(original, token);

      return chain.proceed(request);
    });

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor(logger());
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);

    httpClient.addInterceptor(logging);
    return httpClient.build();
  }

  private HttpLoggingInterceptor.Logger logger() {
    return message -> Log.i("RETROFIT-TRAVIS", message);
  }

  private Request getRequest(Request original, String token) {
    Request.Builder builder = original.newBuilder()
        .header("User-Agent", "gitskarios-travis")
        .method(original.method(), original.body());

    if (token != null && !token.isEmpty()) {
      builder.header("Authorization", "token " + token);
    }

    if (accept != null && !accept.isEmpty()) {
      if (acceptParam != null && !acceptParam.isEmpty()) {
        accept = accept + "; " + acceptParam + ";";
      }
      builder.header("Accept", accept);
    } else {
      if (acceptParam != null && !acceptParam.isEmpty()) {
        builder.header("Accept", "application/vnd.travis-ci.2+json;" + acceptParam + ";");
      } else {
        builder.header("Accept", "application/vnd.travis-ci.2+json");
      }
    }

    if (contentType != null && !contentType.isEmpty()) {
      builder.header("Content-Type", contentType);
    } else {
      builder.header("Content-Type", "application/json");
    }

    return builder.build();
  }

  public RetrofitWrapper accept(String accept) {
    this.accept = accept;
    return this;
  }

  public RetrofitWrapper acceptParam(String param) {
    this.acceptParam = param;
    return this;
  }

  public RetrofitWrapper contentType(String contentType) {
    this.contentType = contentType;
    return this;
  }
}
