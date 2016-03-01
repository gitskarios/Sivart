package com.alorma.travisdk.repository.builds;

import com.alorma.travisdk.bean.response.TravisBuild;
import com.alorma.travisdk.datasource.builds.GetBuildDataSource;

public class GetBuildRepositoryImpl implements GetBuildRepository {

  private final GetBuildDataSource cache;
  private final GetBuildDataSource api;

  public GetBuildRepositoryImpl(GetBuildDataSource cache, GetBuildDataSource api) {

    this.cache = cache;
    this.api = api;
  }

  @Override
  public TravisBuild get(long repoId, long buildId) throws Exception {
    TravisBuild travisBuild = cache.get(repoId, buildId);
    if (travisBuild == null) {
      travisBuild = api.get(repoId, buildId);
      cache.save(repoId, buildId, travisBuild);
    }
    return travisBuild;
  }
}
