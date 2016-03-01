package com.alorma.travis.ui.presenter.builds;

import com.alorma.travisdk.bean.response.TravisBuild;
import com.alorma.travisdk.interactor.builds.GetBuildInteractor;
import rx.Observable;
import rx.Subscriber;

public class BuildSubscriber implements Observable.OnSubscribe<TravisBuild> {

  private GetBuildInteractor interactor;
  private long repoId;
  private long buildId;

  public BuildSubscriber(GetBuildInteractor interactor, long repoId, long buildId) {
    this.interactor = interactor;
    this.repoId = repoId;
    this.buildId = buildId;
  }

  @Override
  public void call(Subscriber<? super TravisBuild> subscriber) {
    if (!subscriber.isUnsubscribed()) {
      try {
        subscriber.onStart();
        subscriber.onNext(interactor.get(repoId, buildId));
        subscriber.onCompleted();
      } catch (Exception e) {
        subscriber.onError(e);
      }
    }
  }
}
