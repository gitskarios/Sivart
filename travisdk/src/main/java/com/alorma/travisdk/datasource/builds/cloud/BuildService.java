package com.alorma.travisdk.datasource.builds.cloud;

import com.alorma.travisdk.bean.response.TravisBuild;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BuildService {

  @GET("builds/{build_id}")
  Call<TravisBuild> getBuild(@Path("build_id") long buildId);

  @GET("repos/{repository_id}/builds/{build_id}")
  Call<TravisBuild> getBuildWithRepoId(@Path("repository_id") long repositoryId,
      @Path("build_id") long buildId);
}
