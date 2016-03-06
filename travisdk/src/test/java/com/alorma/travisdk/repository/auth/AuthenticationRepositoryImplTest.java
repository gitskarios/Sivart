package com.alorma.travisdk.repository.auth;

import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.datasource.login.CredentialsDataSource;
import com.alorma.travisdk.repository.credentials.AuthenticationRepository;
import com.alorma.travisdk.repository.credentials.AuthenticationRepositoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthenticationRepositoryImplTest {

  @Mock Credential TRAVIS_CREDENTIAL;

  @Mock CredentialsDataSource cacheDataSource;
  @Mock CredentialsDataSource apiDataSource;

  private AuthenticationRepository authenticationRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    authenticationRepository = new AuthenticationRepositoryImpl(cacheDataSource, apiDataSource);
  }

  @Test
  public void ShouldReturnCredentials_whenCacheContainsUser()  throws Exception {
    Credential credential = new Credential();
    credential.setName("ferf");
    credential.setGithubToken("");
    when(cacheDataSource.authenticate(eq(credential))).thenReturn(TRAVIS_CREDENTIAL);

    Credential travisToken = authenticationRepository.authenticate(credential);

    assertThat(travisToken).isEqualTo(TRAVIS_CREDENTIAL);
  }

  @Test
  public void ShouldReturnCredentials_whenCacheNoContainsUser()  throws Exception {
    Credential credential = new Credential();
    credential.setName("ferf");
    credential.setGithubToken("");
    when(cacheDataSource.authenticate(eq(credential))).thenReturn(null);
    when(apiDataSource.authenticate(eq(credential))).thenReturn(TRAVIS_CREDENTIAL);

    Credential travisToken = authenticationRepository.authenticate(credential);

    assertThat(travisToken).isNotNull();
  }

  @Test
  public void ShouldCallSave_whenSaveWithValidData()  throws Exception {
    authenticationRepository.save(TRAVIS_CREDENTIAL);

    verify(cacheDataSource).saveCredential(eq(TRAVIS_CREDENTIAL));
  }
}