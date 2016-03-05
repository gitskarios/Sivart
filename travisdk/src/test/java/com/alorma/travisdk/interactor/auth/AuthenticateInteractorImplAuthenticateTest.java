package com.alorma.travisdk.interactor.auth;

import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.repository.credentials.AuthenticationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class AuthenticateInteractorImplAuthenticateTest {

  @Mock Credential TRAVIS_TOKEN;

  @Mock AuthenticationRepository repository;
  private AuthenticateInteractor authenticateInteractor;

  @Before
  public void setUp () {
    MockitoAnnotations.initMocks(this);
    authenticateInteractor = new AuthenticateInteractorImpl(repository);
  }

  //region authenticate
  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenCredentialNull () throws Exception {
    authenticateInteractor.authenticate(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenCredentialNullUsername() throws Exception {
    Credential credential = new Credential();
    credential.setName(null);
    authenticateInteractor.authenticate(credential);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenCredentialEmptyUsername () throws Exception {
    Credential credential = new Credential();
    credential.setName("");
    authenticateInteractor.authenticate(credential);
  }
  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenCredentialGhTokenNull () throws Exception {
    Credential credential = new Credential();
    credential.setName("ferf");
    credential.setGithubToken(null);
    authenticateInteractor.authenticate(credential);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenCredentialGhTokenEmpty () throws Exception {
    Credential credential = new Credential();
    credential.setName("ferf");
    credential.setGithubToken("");
    authenticateInteractor.authenticate(credential);
  }

  @Test
  public void shouldReturnAccessToken_whenAuthenticateWithNoEmptyValues () throws Exception {
    Credential credential = new Credential();
    credential.setName("ferf");
    credential.setGithubToken("fwefwfw");
    when(repository.authenticate(eq(credential))).thenReturn(TRAVIS_TOKEN);

    Credential accessToken = authenticateInteractor.authenticate(credential);

    assertThat(accessToken).isNotNull();
  }
  //endregion
}