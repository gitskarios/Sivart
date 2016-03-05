package com.alorma.travis.ui.presenter.overview;

import com.alorma.travis.ui.presenter.utils.RetrofitWrapper;
import com.alorma.travisdk.bean.response.RepositoryListResponse;
import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.datasource.repos.TravisRepositoriesDataSource;
import com.alorma.travisdk.datasource.repos.cloud.ReposService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;

public class ApiTravisRepositoriesDataSource implements TravisRepositoriesDataSource {

  private RetrofitWrapper retrofit;
  private Credential credential;

  public ApiTravisRepositoriesDataSource(RetrofitWrapper retrofit) {
    this.retrofit = retrofit;
  }

  @Override
  public Observable<List<RepositoryResponse>> getRepos(String owner, boolean active) {
    return Observable.create(subscriber -> {
      if (!subscriber.isUnsubscribed()) {
        try {
          subscriber.onStart();

          Call<RepositoryListResponse> call =
              retrofit.getRetrofit(credential.getTravisUrl(), credential.getToken())
                  .create(ReposService.class)
                  .getRepos(owner, active);

          Response<RepositoryListResponse> response = call.execute();
          if (response.isSuccess()) {
            subscriber.onNext(response.body().repos);
          } else {
            Observable.error(new Exception(response.errorBody().string()));
          }
          subscriber.onCompleted();
        } catch (Exception e) {
          subscriber.onError(e);
        }
      }
    });
  }

  @Override
  public void save(String owner, List<RepositoryResponse> repos) {

  }

  @Override
  public void setCredential(Credential credential) {
    this.credential = credential;
  }
}
