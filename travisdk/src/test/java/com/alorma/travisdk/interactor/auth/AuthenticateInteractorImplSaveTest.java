package com.alorma.travisdk.interactor.auth;

import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.repository.auth.AuthenticationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class AuthenticateInteractorImplSaveTest {

  private static final Credential CREDENTIAL_NULL = null;

  @Mock Credential CREDENTIAL_EMPTY;

  @Mock Credential CREDENTIAL_USERNAME;

  @Mock Credential CREDENTIAL_USERNAME_TOKEN;

  @Mock Credential TRAVIS_CREDENTIAL;

  @Mock AuthenticationRepository repository;
  private AuthenticateInteractor authenticateInteractor;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    CREDENTIAL_EMPTY.setToken(null);

    CREDENTIAL_USERNAME.setName("alorma");
    CREDENTIAL_USERNAME.setToken(null);

    CREDENTIAL_USERNAME_TOKEN.setName("alorma");
    CREDENTIAL_USERNAME_TOKEN.setToken("dhwouehro3");
    authenticateInteractor = new AuthenticateInteractorImpl(repository);
  }

  //region save
  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenSaveWithNullValues() {
    authenticateInteractor.save(CREDENTIAL_NULL);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenSaveWithEmptyValues() {
    authenticateInteractor.save(CREDENTIAL_EMPTY);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenSaveWithEmptyUsernameNullGithubToken() {
    authenticateInteractor.save(CREDENTIAL_EMPTY);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenSaveWithAnyUsernameNullGithubToken() {
    authenticateInteractor.save(CREDENTIAL_USERNAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenSaveWithNullUsernameEmptyGithubToken() {
    Credential credential = new Credential();
    credential.setName(null);
    credential.setToken("");
    authenticateInteractor.save(credential);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenSaveWithNullUsernameAnyGithubToken() {
    Credential credential = new Credential();
    credential.setName(null);
    credential.setToken("dhwouehro3");
    authenticateInteractor.save(credential);
  }

  @Test
  public void shouldCallRepositorySave() {
    Credential credential = new Credential();
    credential.setName("alorma");
    credential.setToken("dhwouehro3");

    authenticateInteractor.save(credential);

    verify(repository).save(eq(credential));
  }
  //endregion
}