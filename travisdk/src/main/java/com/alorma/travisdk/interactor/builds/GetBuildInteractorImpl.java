package com.alorma.travisdk.interactor.builds;

import com.alorma.travisdk.bean.response.TravisBuild;
import com.alorma.travisdk.repository.builds.GetBuildRepository;

public class GetBuildInteractorImpl implements GetBuildInteractor {

  private GetBuildRepository getBuildRepository;

  public GetBuildInteractorImpl(GetBuildRepository getBuildRepository) {
    this.getBuildRepository = getBuildRepository;
  }

  @Override
  public TravisBuild get(long repoId, long buildId) throws Exception {
    if (repoId < 0) {
      throw new IllegalArgumentException("repoId cannot be negative");
    } else if (repoId == 0) {
      throw new IllegalArgumentException("repoId cannot be zero");
    }

    if (buildId < 0) {
      throw new IllegalArgumentException("buildId cannot be negative");
    } else if (buildId == 0) {
      throw new IllegalArgumentException("buildId cannot be zero");
    }
    return getBuildRepository.get(repoId, buildId);
  }
}
