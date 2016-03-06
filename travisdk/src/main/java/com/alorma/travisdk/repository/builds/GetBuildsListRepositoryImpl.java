package com.alorma.travisdk.repository.builds;

import com.alorma.travisdk.bean.response.TravisBuildResponse;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.datasource.builds.GetBuildDataSource;
import java.util.List;
import rx.Observable;

public class GetBuildsListRepositoryImpl implements GetBuildsListRepository {

  private final GetBuildDataSource cache;
  private final GetBuildDataSource api;

  public GetBuildsListRepositoryImpl(GetBuildDataSource cache, GetBuildDataSource api) {
    this.cache = cache;
    this.api = api;
  }

  @Override
  public Observable<List<TravisBuildResponse>> get(String owner, String name) {
    Observable<List<TravisBuildResponse>> cacheObs = cache.get(owner, name);
    Observable<List<TravisBuildResponse>> apiObs = api.get(owner, name);
    apiObs.doOnNext(build -> cache.save(owner, name, build));

    return Observable.concat(cacheObs, apiObs)
        .filter(builds -> builds != null && !builds.isEmpty());
  }

  @Override
  public void setCredential(Credential credential) {
    api.setCredential(credential);
  }
}
