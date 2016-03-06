package com.alorma.travisdk.datasource.builds.cache;

import com.alorma.travisdk.bean.response.TravisBuild;
import com.alorma.travisdk.bean.response.TravisBuildResponse;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.datasource.builds.GetBuildDataSource;
import java.util.List;
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
  public Observable<List<TravisBuildResponse>> get(String owner, String name) {
    return Observable.just(CacheBuildWrapper.get(owner, name));
  }

  @Override
  public void save(String owner, String name, List<TravisBuildResponse> builds) {
    CacheBuildWrapper.set(owner, name, builds);
  }

  @Override
  public void setCredential(Credential credential) {

  }
}
