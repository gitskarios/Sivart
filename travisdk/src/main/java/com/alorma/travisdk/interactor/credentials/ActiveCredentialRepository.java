package com.alorma.travisdk.interactor.credentials;

import com.alorma.travisdk.bean.utils.Credential;
import rx.Observable;

public interface ActiveCredentialRepository {

  void set(Credential credential);

  Credential get();

  Observable<Credential> getCredential();
}
