package com.alorma.travisdk.interactor.logs;

public interface GetJobLogInteractor {
  String getLog(long jobId) throws Exception;
}
