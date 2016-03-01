package com.alorma.travisdk.datasource.login;

import com.alorma.travisdk.bean.utils.Credential;
import java.util.List;

public interface CredentialsDataSource {
  List<Credential> getCredentials();

  Credential authenticate(Credential credential) throws Exception;

  void saveCredential(Credential credential);
}
