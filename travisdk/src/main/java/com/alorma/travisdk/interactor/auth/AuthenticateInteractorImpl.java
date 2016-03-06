package com.alorma.travisdk.interactor.auth;

import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.repository.credentials.AuthenticationRepository;

public class AuthenticateInteractorImpl implements AuthenticateInteractor {

  private AuthenticationRepository authenticationRepository;

  public AuthenticateInteractorImpl(AuthenticationRepository authenticationRepository) {
    this.authenticationRepository = authenticationRepository;
  }

  @Override
  public Credential authenticate(Credential credential) throws Exception{
    if (credential == null) {
      throw new IllegalArgumentException("Invalid parameters: [credential = is null]");
    }

    if (credential.getName() == null || credential.getName().isEmpty()) {
      throw new IllegalArgumentException(
          "Invalid parameters: [username = " + credential.getName() + "]");
    }
    if (credential.getGithubToken() == null || credential.getGithubToken().isEmpty()) {
      throw new IllegalArgumentException(
          "Invalid parameters: [github_token = " + credential.getGithubToken() + "]");
    }

    Credential authenticatedCredential = authenticationRepository.authenticate(credential);
    authenticationRepository.save(authenticatedCredential);

    return authenticatedCredential;
  }

  @Override
  public void save(Credential credential) {
    if (credential == null || credential.getName() == null || credential.getName().isEmpty()) {
      throw new IllegalArgumentException(
          "Invalid parameters: [credentials = " + String.valueOf(credential) + "]");
    }
    authenticationRepository.save(credential);
  }
}
