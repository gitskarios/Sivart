package com.alorma.travisdk.repository.credentials;

import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.datasource.login.CredentialsDataSource;
import java.util.List;
import rx.Observable;

public class CredentialsRepositoryImpl implements CredentialsRepository{
  private CredentialsDataSource dataSource;

  public CredentialsRepositoryImpl(CredentialsDataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public List<Credential> getCredentials() {
    return dataSource.getCredentials();
  }

  @Override
  public Observable<List<Credential>> getCredentialsObs() {
    return dataSource.getCredentialsObs();
  }
}
