package com.alorma.travis.ui.presenter.utils;

import android.util.Log;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitWrapper {

  public RetrofitWrapper() {

  }

  public Retrofit getRetrofit(String url) {
    return createRetrofit(url);
  }

  protected Retrofit createRetrofit(String url) {
    Retrofit.Builder builder = new Retrofit.Builder();
    builder.baseUrl(() -> HttpUrl.parse(url));
    builder.addConverterFactory(GsonConverterFactory.create());
    builder.client(createHttpClient());

    return builder.build();
  }

  protected OkHttpClient createHttpClient() {
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    httpClient.addInterceptor(chain -> {
      Request original = chain.request();

      Request request = getRequest(original);

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

  private Request getRequest(Request original) {
    Request.Builder builder = original.newBuilder()
        .header("User-Agent", "gitskarios-travis")
        .header("Content-Type", "application/json")
        .header("Accept", "application/vnd.travis-ci.2+json")
        .method(original.method(), original.body());

    builder = customizeRequest(builder);

    return builder.build();
  }

  protected Request.Builder customizeRequest(Request.Builder builder) {
    return builder;
  }

}
