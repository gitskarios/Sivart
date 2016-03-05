package com.alorma.travisdk.datasource.builds.cache;

import com.alorma.travisdk.bean.response.TravisBuild;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.datasource.builds.GetBuildDataSource;
import rx.Observable;

public class CacheGetBuildDataSource implements GetBuildDataSource {
  @Override
  public Observable<TravisBuild> get(long repoId, long buildId) {
    return Observable.just(CacheBuildWrapper.get(repoId, buildId));
  }

  @Override
  public void save(long repoId, long buildId, TravisBuild build) {
    CacheBuildWrapper.set(repoId, buildId, build);
  }

  @Override
  public void setCredential(Credential credential) {

  }
}
