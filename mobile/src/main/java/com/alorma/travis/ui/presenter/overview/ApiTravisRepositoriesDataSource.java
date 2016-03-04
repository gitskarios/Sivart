package com.alorma.travis.ui.presenter.overview;

import com.alorma.travis.ui.presenter.utils.RetrofitWrapper;
import com.alorma.travisdk.bean.response.RepositoryListResponse;
import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.datasource.repos.TravisRepositoriesDataSource;
import com.alorma.travisdk.datasource.repos.cloud.ReposService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;

public class ApiTravisRepositoriesDataSource implements TravisRepositoriesDataSource {

  private RetrofitWrapper retrofit;
  private String url;
  private String token;

  public ApiTravisRepositoriesDataSource(RetrofitWrapper retrofit, String url, String token) {
    this.retrofit = retrofit;
    this.url = url;
    this.token = token;
  }

  @Override
  public List<RepositoryResponse> getRepos(String owner, boolean active) throws Exception {
    Call<RepositoryListResponse> call =
        retrofit.getRetrofit(url, token).create(ReposService.class).getRepos(owner, active);

    Response<RepositoryListResponse> response = call.execute();
    if (response.isSuccess()) {
      return response.body().repos;
    } else {
      throw new Exception(response.errorBody().string());
    }
  }

  @Override
  public void save(String owner, List<RepositoryResponse> repos) {

  }
}
