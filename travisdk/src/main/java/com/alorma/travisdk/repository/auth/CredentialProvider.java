package com.alorma.travisdk.repository.auth;

import com.alorma.travisdk.bean.utils.Credential;

public interface CredentialProvider {
  void setCredential(Credential credential);
}
