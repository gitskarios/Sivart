package com.alorma.travisdk.repository.auth;

import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.datasource.login.CredentialsDataSource;

public class AuthenticationRepositoryImpl implements AuthenticationRepository {

  private CredentialsDataSource apiDataSource;
  private CredentialsDataSource cacheDataSource;

  public AuthenticationRepositoryImpl(CredentialsDataSource cacheDataSource,
      CredentialsDataSource apiDataSource) {
    this.apiDataSource = apiDataSource;
    this.cacheDataSource = cacheDataSource;
  }

  @Override
  public Credential authenticate(Credential credential) throws Exception{
    Credential authenticated = apiDataSource.authenticate(credential);
    if (authenticated == null) {
      authenticated = cacheDataSource.authenticate(credential);
    }
    return authenticated;
  }

  @Override
  public void save(Credential credential) {
    cacheDataSource.saveCredential(credential);
  }
}
