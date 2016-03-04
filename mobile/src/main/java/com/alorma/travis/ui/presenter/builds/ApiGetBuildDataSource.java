package com.alorma.travis.ui.presenter.builds;

import com.alorma.travis.ui.presenter.utils.RetrofitWrapper;
import com.alorma.travisdk.bean.response.TravisBuild;
import com.alorma.travisdk.datasource.builds.GetBuildDataSource;
import com.alorma.travisdk.datasource.builds.cloud.BuildService;
import retrofit2.Call;
import retrofit2.Response;

public class ApiGetBuildDataSource implements GetBuildDataSource {

  private final RetrofitWrapper retrofit;
  private final String url;
  private String token;

  public ApiGetBuildDataSource(RetrofitWrapper retrofit, String url, String token) {
    this.retrofit = retrofit;
    this.url = url;
    this.token = token;
  }

  @Override
  public TravisBuild get(long repoId, long buildId) throws Exception {
    Call<TravisBuild> call =
        retrofit.getRetrofit(url, token).create(BuildService.class).getBuild(buildId);

    Response<TravisBuild> response = call.execute();
    if (response.isSuccess()) {
      return response.body();
    } else {
      throw new Exception(response.errorBody().string());
    }
  }

  @Override
  public void save(long repoId, long buildId, TravisBuild build) {

  }
}
