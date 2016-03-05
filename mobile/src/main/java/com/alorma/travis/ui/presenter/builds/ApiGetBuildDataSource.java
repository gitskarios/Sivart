package com.alorma.travis.ui.presenter.builds;

import com.alorma.travis.ui.presenter.utils.RetrofitWrapper;
import com.alorma.travisdk.bean.response.TravisBuild;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.datasource.builds.GetBuildDataSource;
import com.alorma.travisdk.datasource.builds.cloud.BuildService;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;

public class ApiGetBuildDataSource implements GetBuildDataSource {

  private final RetrofitWrapper retrofit;
  private Credential credential;

  public ApiGetBuildDataSource(RetrofitWrapper retrofit) {
    this.retrofit = retrofit;
  }

  @Override
  public Observable<TravisBuild> get(long repoId, long buildId) {
    return Observable.create((Observable.OnSubscribe<TravisBuild>) subscriber -> {
      if (!subscriber.isUnsubscribed()) {
        try {
          subscriber.onStart();
          Response<TravisBuild> response = getTravisBuildCall(buildId).execute();

          if (response.isSuccess()) {
            subscriber.onNext(response.body());
            subscriber.onCompleted();
          } else {
            subscriber.onError(new Exception(response.errorBody().string()));
          }
        } catch (Exception e) {
          subscriber.onError(e);
        }
      }
    });
  }

  private Call<TravisBuild> getTravisBuildCall(long buildId) {
    return retrofit.getRetrofit(credential.getTravisUrl(), credential.getToken())
        .create(BuildService.class)
        .getBuild(buildId);
  }

  @Override
  public void save(long repoId, long buildId, TravisBuild build) {

  }

  @Override
  public void setCredential(Credential credential) {
    this.credential = credential;
  }
}
