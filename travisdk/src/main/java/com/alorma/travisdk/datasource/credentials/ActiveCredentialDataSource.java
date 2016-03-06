package com.alorma.travisdk.datasource.credentials;

import com.alorma.travisdk.bean.utils.Credential;

public interface ActiveCredentialDataSource {

  void set(Credential credential);

  Credential get();
}
