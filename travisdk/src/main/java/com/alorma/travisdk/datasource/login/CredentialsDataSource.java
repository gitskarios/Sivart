package com.alorma.travisdk.datasource.login;

import com.alorma.travisdk.bean.utils.Credential;
import java.util.List;
import rx.Observable;

public interface CredentialsDataSource {
  List<Credential> getCredentials();
  Observable<List<Credential>> getCredentialsObs();

  Credential authenticate(Credential credential) throws Exception;

  void saveCredential(Credential credential);
}
