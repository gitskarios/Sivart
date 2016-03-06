package com.alorma.travisdk.repository.builds;

import com.alorma.travisdk.bean.response.TravisBuildsListResponse;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.datasource.builds.GetBuildDataSource;
import rx.Observable;

public class GetBuildsListRepositoryImpl implements GetBuildsListRepository {

  private final GetBuildDataSource cache;
  private final GetBuildDataSource api;

  public GetBuildsListRepositoryImpl(GetBuildDataSource cache, GetBuildDataSource api) {
    this.cache = cache;
    this.api = api;
  }

  @Override
  public Observable<TravisBuildsListResponse> get(String owner, String name) {
    Observable<TravisBuildsListResponse> cacheObs = cache.get(owner, name);
    Observable<TravisBuildsListResponse> apiObs = api.get(owner, name);
    apiObs.doOnNext(build -> cache.save(owner, name, build));

    return Observable.concat(cacheObs, apiObs)
        .filter(builds -> builds != null);
  }

  @Override
  public void setCredential(Credential credential) {
    api.setCredential(credential);
  }
}
