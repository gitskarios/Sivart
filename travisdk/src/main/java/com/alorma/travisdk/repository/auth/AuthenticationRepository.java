package com.alorma.travisdk.repository.auth;

import com.alorma.travisdk.bean.utils.Credential;

public interface AuthenticationRepository {
  Credential authenticate(Credential credential) throws Exception;
  void save(Credential credential);
}
