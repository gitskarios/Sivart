package com.alorma.travisdk.interactor.repos;

import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.interactor.accounts.ActiveCredentialRepositoryImpl;
import com.alorma.travisdk.repository.repos.TravisRepositoriesRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class GetTravisRepositoriesInteractorImplTest {

  private static final List<RepositoryResponse> NULL_LIST = null;
  private static final List<RepositoryResponse> EMPTY_LIST = Collections.emptyList();
  private static final List<RepositoryResponse> ANY_LIST = new ArrayList<>();

  @Mock TravisRepositoriesRepository repository;
  private GetTravisRepositoriesInteractorImpl repositoriesInteractor;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    repositoriesInteractor = new GetTravisRepositoriesInteractorImpl(repository,
        ActiveCredentialRepositoryImpl.getInstance());

    ANY_LIST.add(new RepositoryResponse());
    ANY_LIST.add(new RepositoryResponse());
  }

  @Test
  public void shouldReturnEmptyList_whenNull() throws Exception {
    when(repository.getRepos(anyString(), anyBoolean())).thenReturn(NULL_LIST);

    List<RepositoryResponse> list = repositoriesInteractor.getRepos(anyString(), anyBoolean());

    assertThat(list).isEmpty();
  }

  @Test
  public void shouldReturnEmptyList_whenNoRepositoriesAvailable() throws Exception {
    when(repository.getRepos(anyString(), anyBoolean())).thenReturn(EMPTY_LIST);

    List<RepositoryResponse> list = repositoriesInteractor.getRepos(anyString(), anyBoolean());

    assertThat(list).isEmpty();
  }

  @Test
  public void shouldReturnRepositoriesList_whenAnyRepositoriesAvailable() throws Exception {
    when(repository.getRepos(anyString(), anyBoolean())).thenReturn(ANY_LIST);

    List<RepositoryResponse> list = repositoriesInteractor.getRepos(anyString(), anyBoolean());

    assertThat(list.size()).isEqualTo(ANY_LIST.size());
  }
}