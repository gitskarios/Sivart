package com.alorma.travisdk.interactor.logs;

import com.alorma.travisdk.repository.logs.GetLogRepository;

public class GetJobLogInteractorImpl implements GetJobLogInteractor {
  private GetLogRepository repository;

  public GetJobLogInteractorImpl(GetLogRepository repository) {

    this.repository = repository;
  }

  @Override
  public String getLog(long jobId) throws Exception {
    return repository.getLog(jobId);
  }
}
