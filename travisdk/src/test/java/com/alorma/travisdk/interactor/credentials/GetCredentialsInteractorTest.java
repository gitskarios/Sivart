package com.alorma.travisdk.interactor.credentials;

import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.repository.credentials.CredentialsRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetCredentialsInteractorTest {

  @Mock private CredentialsRepository credentialsRepository;

  @Mock private Credential ALORMA;
  @Mock private Credential GITSKARIOS;

  private List<Credential> credentials;

  private GetCredentialsInteractor interactor;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    credentials = new ArrayList<>();
    interactor = new GetCredentialsInteractorImpl(credentialsRepository);
  }

  @Test
  public void ShouldReturnEmptyList_whenNullCredentials() {
    when(credentialsRepository.getCredentials()).thenReturn(null);

    List<Credential> credentials = interactor.getCredentials();

    assertThat(credentials).isNotNull().isEmpty();
  }

  @Test
  public void ShouldReturnEmptyList_whenNoCredentialsAvailable() {
    when(credentialsRepository.getCredentials()).thenReturn(credentials);

    List<Credential> credentials = interactor.getCredentials();

    assertThat(credentials).isNotNull().isEmpty();
  }

  @Test
  public void ShouldReturnList_whenCredentialsAvailable() {
    credentials.add(ALORMA);
    credentials.add(GITSKARIOS);

    when(credentialsRepository.getCredentials()).thenReturn(credentials);

    List<Credential> credentials = interactor.getCredentials();

    assertThat(credentials).isNotNull().isNotEmpty().hasSameElementsAs(credentials);
  }
}