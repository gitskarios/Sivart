package com.alorma.travisdk.datasource.builds;

import com.alorma.travisdk.bean.response.TravisBuild;

public interface GetBuildDataSource {
  TravisBuild get(long repoId, long buildId) throws Exception;
  void save(long repoId, long buildId, TravisBuild build);
}
