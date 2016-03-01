package com.alorma.travisdk.repository.auth;

import com.alorma.travisdk.bean.request.GithubTokenRequest;
import com.alorma.travisdk.bean.response.AccessTokenResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

  @POST("/auth/github")
  Call<AccessTokenResponse> githubToken(@Body GithubTokenRequest githubTokenRequest);
}