package com.alorma.travisdk.datasource.accounts.cloud;

import com.alorma.travisdk.bean.response.GithubAccount;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GithubAccountsService {

  @GET("/user")
  Call<GithubAccount> me();

  @GET("/user/orgs")
  Call<List<GithubAccount>> orgs();
}
