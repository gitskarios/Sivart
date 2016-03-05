package com.alorma.travisdk.interactor.builds;

import com.alorma.travisdk.bean.response.TravisBuild;
import rx.Observable;

public interface GetBuildInteractor {
  Observable<TravisBuild> get(long repoId, long buildId);
}
