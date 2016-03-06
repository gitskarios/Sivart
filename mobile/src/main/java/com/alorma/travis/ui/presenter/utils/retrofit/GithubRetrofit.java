package com.alorma.travis.ui.presenter.utils.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubRetrofit extends RetrofitWrapper {
  @Override
  protected Retrofit buildRetrofit(String accept, String contentType, String token, String url) {
    if (url == null) {
      url = "https://api.github.com";
    }

    Retrofit.Builder builder = new Retrofit.Builder();

    builder.baseUrl(url);
    builder.addConverterFactory(GsonConverterFactory.create());

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    httpClient.addInterceptor(chain -> {
      Request original = chain.request();

      Request request = getRequest(original, accept, contentType, token);

      return chain.proceed(request);
    });

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor(logger());
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    httpClient.addInterceptor(logging);

    builder.client(httpClient.build());

    return builder.build();
  }

  private Request getRequest(Request original, String accept, String contentType, String token) {
    Request.Builder builder = original.newBuilder()
        .header("User-Agent", "gitskarios-travis")
        .method(original.method(), original.body());

    if (token != null) {
      builder.header("Authorization", "token " + token);
    }
    if (accept != null) {
      builder.header("Accept", accept);
    }
    if (contentType != null) {
      builder.header("Content-Type", contentType);
    }
    return builder.build();
  }
}
