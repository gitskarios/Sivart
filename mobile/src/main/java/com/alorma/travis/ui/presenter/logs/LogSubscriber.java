package com.alorma.travis.ui.presenter.logs;

import com.alorma.travisdk.bean.response.TravisJobResponse;
import com.alorma.travisdk.interactor.logs.GetJobLogInteractor;
import rx.Observable;
import rx.Subscriber;

public class LogSubscriber implements Observable.OnSubscribe<String> {

  private GetJobLogInteractor jobLogInteractor;
  private TravisJobResponse travisJobResponse;

  public LogSubscriber(GetJobLogInteractor jobLogInteractor, TravisJobResponse travisJobResponse) {

    this.jobLogInteractor = jobLogInteractor;
    this.travisJobResponse = travisJobResponse;
  }

  @Override
  public void call(Subscriber<? super String> subscriber) {
    if (!subscriber.isUnsubscribed()) {
      try {
        subscriber.onStart();
        subscriber.onNext(jobLogInteractor.getLog(travisJobResponse.getId()));
        subscriber.onCompleted();
      } catch (Exception e) {
        subscriber.onError(e);
      }
    }
  }
}
