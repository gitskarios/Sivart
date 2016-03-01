package com.alorma.travisdk.interactor.accounts;

import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.repository.credentials.CredentialsRepository;
import java.util.ArrayList;
import java.util.List;

public class GetCredentialsInteractorImpl implements GetCredentialsInteractor {

  private CredentialsRepository credentialsRepository;

  public GetCredentialsInteractorImpl(CredentialsRepository credentialsRepository) {
    this.credentialsRepository = credentialsRepository;
  }

  @Override
  public List<Credential> getCredentials() {
    List<Credential> credentials = credentialsRepository.getCredentials();
    if (credentials == null) {
      credentials = new ArrayList<>();
    }
    return credentials;
  }
}
