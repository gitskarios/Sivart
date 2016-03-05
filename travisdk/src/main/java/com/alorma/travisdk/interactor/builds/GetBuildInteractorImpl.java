package com.alorma.travisdk.interactor.builds;

import com.alorma.travisdk.bean.response.TravisBuild;
import com.alorma.travisdk.repository.builds.GetBuildRepository;
import rx.Observable;

public class GetBuildInteractorImpl implements GetBuildInteractor {

  private GetBuildRepository getBuildRepository;

  public GetBuildInteractorImpl(GetBuildRepository getBuildRepository) {
    this.getBuildRepository = getBuildRepository;
  }

  @Override
  public Observable<TravisBuild> get(long repoId, long buildId) {
    if (repoId < 0) {
      return Observable.error(new IllegalArgumentException("repoId cannot be negative"));
    } else if (repoId == 0) {
      return Observable.error(new IllegalArgumentException("repoId cannot be zero"));
    }

    if (buildId < 0) {
      return Observable.error(new IllegalArgumentException("buildId cannot be negative"));
    } else if (buildId == 0) {
      return Observable.error(new IllegalArgumentException("buildId cannot be zero"));
    }
    return getBuildRepository.get(repoId, buildId);
  }
}
