package com.alorma.travisdk.repository.builds;

import com.alorma.travisdk.bean.response.TravisBuild;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.datasource.builds.GetBuildDataSource;
import rx.Observable;

public class GetBuildRepositoryImpl implements GetBuildRepository {

  private final GetBuildDataSource cache;
  private final GetBuildDataSource api;

  public GetBuildRepositoryImpl(GetBuildDataSource cache, GetBuildDataSource api) {
    this.cache = cache;
    this.api = api;
  }

  @Override
  public Observable<TravisBuild> get(long repoId, long buildId) {
    Observable<TravisBuild> cacheObs = cache.get(repoId, buildId);
    Observable<TravisBuild> apiObs = api.get(repoId, buildId);
    apiObs.doOnNext(build -> cache.save(repoId, buildId, build));

    return Observable.concat(cacheObs, apiObs).filter(build -> build != null);
  }

  @Override
  public void setCredential(Credential credential) {
    api.setCredential(credential);
  }
}
