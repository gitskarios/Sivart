package com.alorma.travisdk.interactor.auth;

import com.alorma.travisdk.bean.utils.Credential;

public interface AuthenticateInteractor {
  Credential authenticate(Credential credential) throws Exception;
  void save(Credential credential);
}
