package com.alorma.travisdk.interactor.repos;

import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.interactor.accounts.ActiveCredentialRepository;
import com.alorma.travisdk.interactor.accounts.ActiveCredentialRepositoryImpl;
import com.alorma.travisdk.repository.repos.TravisRepositoriesRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class GetTravisRepositoriesInteractorImplTest {

  private static final List<RepositoryResponse> NULL_LIST = null;
  private static final List<RepositoryResponse> EMPTY_LIST = Collections.emptyList();
  private static final List<RepositoryResponse> ANY_LIST = new ArrayList<>();

  @Mock TravisRepositoriesRepository repository;
  private GetTravisRepositoriesInteractorImpl repositoriesInteractor;
  private TestSubscriber<List<RepositoryResponse>> testSubscriber;
  private ActiveCredentialRepository credentialRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    credentialRepository =
        ActiveCredentialRepositoryImpl.getInstance();
    repositoriesInteractor =
        new GetTravisRepositoriesInteractorImpl(repository, credentialRepository);

    ANY_LIST.add(new RepositoryResponse());
    ANY_LIST.add(new RepositoryResponse());

    testSubscriber = new TestSubscriber<>();

    credentialRepository.set(new Credential());
  }

  @Test
  public void shouldReturnEmptyList_whenNull() throws Exception {
    when(repository.getRepos(anyString(), anyBoolean())).thenReturn(Observable.just(NULL_LIST));

    Observable<List<RepositoryResponse>> list =
        repositoriesInteractor.getRepos(anyString(), anyBoolean());

    list.subscribe(testSubscriber);

    testSubscriber.assertNoValues();
  }

  @Test
  public void shouldReturnEmptyList_whenNoRepositoriesAvailable() throws Exception {
    when(repository.getRepos(anyString(), anyBoolean())).thenReturn(Observable.just(EMPTY_LIST));

    Observable<List<RepositoryResponse>> list =
        repositoriesInteractor.getRepos(anyString(), anyBoolean());

    list.subscribe(testSubscriber);

    testSubscriber.assertNoValues();
  }

  /*
  @Test
  public void shouldReturnRepositoriesList_whenAnyRepositoriesAvailable() throws Exception {
    when(repository.getRepos(anyString(), anyBoolean())).thenReturn(Observable.just(ANY_LIST));

    credentialRepository.set(new Credential());

    Observable<List<RepositoryResponse>> list =
        repositoriesInteractor.getRepos(anyString(), anyBoolean());

    list.subscribe(testSubscriber);

    testSubscriber.assertValue(ANY_LIST);
  }
  */
}