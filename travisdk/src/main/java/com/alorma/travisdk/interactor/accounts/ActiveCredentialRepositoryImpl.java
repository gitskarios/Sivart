package com.alorma.travisdk.interactor.accounts;

import com.alorma.travisdk.bean.utils.Credential;
import rx.Observable;

public class ActiveCredentialRepositoryImpl implements ActiveCredentialRepository {
  private Credential credential;
  private static ActiveCredentialRepositoryImpl INSTANCE;

  public static ActiveCredentialRepository getInstance() {
    if (INSTANCE == null) {
      throw new IllegalStateException("ActiveCredentialRepository has not been initialized");
    }
    return INSTANCE;
  }

  public static ActiveCredentialRepository init() {
    if (INSTANCE == null) {
      INSTANCE = new ActiveCredentialRepositoryImpl();
    }
    return INSTANCE;
  }

  private ActiveCredentialRepositoryImpl() {

  }

  @Override
  public void set(Credential credential) {
    this.credential = credential;
  }

  @Override
  public Credential get() {
    if (credential == null) {
      throw new IllegalStateException("No active credential");
    }
    return credential;
  }

  @Override
  public Observable<Credential> getCredential() {
    return Observable.create(subscriber -> {
      if (!subscriber.isUnsubscribed()) {
        try {
          subscriber.onStart();
          subscriber.onNext(get());
          subscriber.onCompleted();
        } catch (IllegalStateException e) {
          subscriber.onError(e);
        }
      }
    });
  }
}
