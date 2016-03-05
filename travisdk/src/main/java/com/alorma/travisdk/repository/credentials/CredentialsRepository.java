package com.alorma.travisdk.repository.credentials;

import com.alorma.travisdk.bean.utils.Credential;
import java.util.List;
import rx.Observable;

public interface CredentialsRepository {
  List<Credential> getCredentials();

  Observable<List<Credential>> getCredentialsObs();
}
