package com.alorma.travisdk.repository.builds;

import com.alorma.travisdk.bean.response.TravisBuild;

public interface GetBuildRepository {
  TravisBuild get(long repoId, long buildId) throws Exception;
}
