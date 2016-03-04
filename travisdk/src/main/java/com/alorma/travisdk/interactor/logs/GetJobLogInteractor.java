package com.alorma.travisdk.interactor.logs;

import rx.Observable;

public interface GetJobLogInteractor {
  String getLog(long jobId) throws Exception;
  Observable<String> getLogObservabe(long jobId);
}
