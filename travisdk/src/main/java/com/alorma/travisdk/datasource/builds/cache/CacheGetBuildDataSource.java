package com.alorma.travisdk.datasource.builds.cache;

import com.alorma.travisdk.bean.response.TravisBuild;
import com.alorma.travisdk.datasource.builds.GetBuildDataSource;

public class CacheGetBuildDataSource implements GetBuildDataSource {
  @Override
  public TravisBuild get(long repoId, long buildId) throws Exception {
    return CacheBuildWrapper.get(repoId, buildId);
  }

  @Override
  public void save(long repoId, long buildId, TravisBuild build) {
    CacheBuildWrapper.set(repoId, buildId, build);
  }
}
