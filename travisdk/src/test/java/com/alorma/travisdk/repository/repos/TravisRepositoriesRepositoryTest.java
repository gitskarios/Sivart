package com.alorma.travisdk.repository.repos;

import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.datasource.repos.TravisRepositoriesDataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class TravisRepositoriesRepositoryTest {

  private static final List<RepositoryResponse> NULL_LIST = null;
  private static final List<RepositoryResponse> EMPTY_LIST = Collections.emptyList();
  private static final List<RepositoryResponse> ANY_LIST = new ArrayList<>();

  @Mock TravisRepositoriesDataSource cacheDatasource;
  @Mock TravisRepositoriesDataSource apiDatasource;

  private TravisRepositoriesRepositoryImpl repository;
  private TestSubscriber<List<RepositoryResponse>> testSubscriber;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    repository = new TravisRepositoriesRepositoryImpl(cacheDatasource, apiDatasource);

    testSubscriber = new TestSubscriber<>();

    ANY_LIST.add(new RepositoryResponse());
    ANY_LIST.add(new RepositoryResponse());
  }

  @Test
  public void shouldReturnNull_whenNoCacheAndNoApi() throws Exception {
    when(cacheDatasource.getRepos(anyString(), anyBoolean())).thenReturn(Observable.just(NULL_LIST));
    when(apiDatasource.getRepos(anyString(), anyBoolean())).thenReturn(Observable.just(NULL_LIST));

    Observable<List<RepositoryResponse>> repos = repository.getRepos(anyString(), anyBoolean());
    repos.subscribe(testSubscriber);

    testSubscriber.assertNoValues();
  }

  @Test
  public void shouldReturnEmpty_whenNoCacheAndEmptyApi() throws Exception {
    when(cacheDatasource.getRepos(anyString(), anyBoolean())).thenReturn(Observable.just(NULL_LIST));
    when(apiDatasource.getRepos(anyString(), anyBoolean())).thenReturn(Observable.just(EMPTY_LIST));

    Observable<List<RepositoryResponse>> repos = repository.getRepos(anyString(), anyBoolean());
    repos.subscribe(testSubscriber);

    testSubscriber.assertNoValues();
  }

  @Test
  public void shouldReturnNotEmpty_whenNoCacheAndAnyApi() throws Exception {
    when(cacheDatasource.getRepos(anyString(), anyBoolean())).thenReturn(Observable.just(NULL_LIST));
    when(apiDatasource.getRepos(anyString(), anyBoolean())).thenReturn(Observable.just(ANY_LIST));

    Observable<List<RepositoryResponse>> repos = repository.getRepos(anyString(), anyBoolean());
    repos.subscribe(testSubscriber);

    testSubscriber.assertValue(ANY_LIST);
  }

  @Test
  public void shouldReturnEmpty_whenEmptyCache() throws Exception {
    when(cacheDatasource.getRepos(anyString(), anyBoolean())).thenReturn(Observable.just(EMPTY_LIST));
    when(apiDatasource.getRepos(anyString(), anyBoolean())).thenReturn(Observable.just(NULL_LIST));

    Observable<List<RepositoryResponse>> repos = repository.getRepos(anyString(), anyBoolean());
    repos.subscribe(testSubscriber);

    testSubscriber.assertNoValues();
  }

  @Test
  public void shouldReturnNotEmpty_whenAnyCache() throws Exception {
    when(cacheDatasource.getRepos(anyString(), anyBoolean())).thenReturn(Observable.just(ANY_LIST));
    when(apiDatasource.getRepos(anyString(), anyBoolean())).thenReturn(Observable.just(NULL_LIST));

    Observable<List<RepositoryResponse>> repos = repository.getRepos(anyString(), anyBoolean());
    repos.subscribe(testSubscriber);

    testSubscriber.assertValue(ANY_LIST);
  }
}