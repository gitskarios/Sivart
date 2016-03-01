package com.alorma.travisdk.repository.credentials;

import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.datasource.login.CredentialsDataSource;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class CredentialsRepositoryTest {

  @Mock private Credential ALORMA;
  @Mock private Credential GITSKARIOS;

  private List<Credential> credentials;

  @Mock
  private CredentialsDataSource dataSource;

  private CredentialsRepositoryImpl repository;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    repository = new CredentialsRepositoryImpl(dataSource);

    credentials = new ArrayList<>();
  }


  @Test
  public void ShouldReturnEmptyList_whenNoCredentialsAvailable() {
    when(dataSource.getCredentials()).thenReturn(credentials);

    List<Credential> credentials = repository.getCredentials();

    assertThat(credentials).isNotNull().isEmpty();
  }

  @Test
  public void ShouldReturnList_whenCredentialsAvailable() {
    credentials.add(ALORMA);
    credentials.add(GITSKARIOS);

    when(dataSource.getCredentials()).thenReturn(credentials);

    List<Credential> credentials = repository.getCredentials();

    assertThat(credentials).isNotNull().isNotEmpty().hasSameElementsAs(credentials);
  }
}