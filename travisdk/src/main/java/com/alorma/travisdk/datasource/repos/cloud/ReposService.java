package com.alorma.travisdk.datasource.repos.cloud;

import com.alorma.travisdk.bean.response.RepositoryListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ReposService {

  @GET("repos")
  Call<RepositoryListResponse> getRepos(@Query("owner_name") String member,
      @Query("active") boolean active);
}
