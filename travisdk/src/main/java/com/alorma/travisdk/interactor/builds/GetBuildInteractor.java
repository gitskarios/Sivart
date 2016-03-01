package com.alorma.travisdk.interactor.builds;

import com.alorma.travisdk.bean.response.TravisBuild;

public interface GetBuildInteractor {
  TravisBuild get(long repoId, long buildId) throws Exception;
}
