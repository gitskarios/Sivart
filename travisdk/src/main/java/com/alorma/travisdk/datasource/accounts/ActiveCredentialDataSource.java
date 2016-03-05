package com.alorma.travisdk.datasource.accounts;

import com.alorma.travisdk.bean.utils.Credential;

public interface ActiveCredentialDataSource {

  void set(Credential credential);

  Credential get();
}
