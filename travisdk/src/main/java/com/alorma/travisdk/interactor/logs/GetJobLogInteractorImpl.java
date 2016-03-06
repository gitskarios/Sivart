package com.alorma.travisdk.interactor.logs;

import com.alorma.travisdk.interactor.credentials.ActiveCredentialRepository;
import com.alorma.travisdk.repository.logs.GetLogRepository;
import rx.Observable;

public class GetJobLogInteractorImpl implements GetJobLogInteractor {
  private GetLogRepository repository;
  private ActiveCredentialRepository credentialRepository;

  public GetJobLogInteractorImpl(GetLogRepository repository,
      ActiveCredentialRepository credentialRepository) {
    this.repository = repository;
    this.credentialRepository = credentialRepository;
  }

  @Override
  public String getLog(long jobId) throws Exception {
    return repository.getLog(jobId);
  }

  @Override
  public Observable<String> getLogObservable(long jobId) {
    return credentialRepository.getCredential().flatMap(credential -> {
      repository.setCredential(credential);
      return logObservable(jobId);
    });
  }

  private Observable<String> logObservable(long jobId) {
    return Observable.create(subscriber -> {
      if (!subscriber.isUnsubscribed()) {
        try {
          subscriber.onStart();
          subscriber.onNext(getLog(jobId));
          subscriber.onCompleted();
        } catch (Exception e) {
          subscriber.onError(e);
        }
      }
    });
  }
}
