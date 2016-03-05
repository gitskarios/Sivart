package com.alorma.travisdk.repository.builds;

import com.alorma.travisdk.bean.response.TravisBuild;
import com.alorma.travisdk.datasource.builds.GetBuildDataSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.when;

public class GetBuildRepositoryTest {

  private int REPO_ID = 23436881;
  private int BUILD_ID = 23436881;

  private GetBuildRepositoryImpl repository;

  @Mock GetBuildDataSource cache;
  @Mock GetBuildDataSource api;

  private TestSubscriber<TravisBuild> testSubscriber;

  @Mock private TravisBuild MOCKED_TRAVIS_BUILD_CACHE;
  @Mock private TravisBuild MOCKED_TRAVIS_BUILD_API;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    repository = new GetBuildRepositoryImpl(cache, api);

    testSubscriber = new TestSubscriber<>();
  }

  @Test
  public void ShouldReturnNull_whenCacheIsNullAndApiIsNull() throws Exception {
    when(cache.get(REPO_ID, BUILD_ID)).thenReturn(Observable.empty());
    when(api.get(REPO_ID, BUILD_ID)).thenReturn(Observable.empty());

    Observable<TravisBuild> observable = repository.get(REPO_ID, BUILD_ID);
    observable.subscribe(testSubscriber);

    testSubscriber.assertNoValues();
  }

  @Test
  public void ShouldReturnNull_whenCacheIsEmptyAndApiIsOk() throws Exception {
    when(cache.get(REPO_ID, BUILD_ID)).thenReturn(Observable.empty());
    when(api.get(REPO_ID, BUILD_ID)).thenReturn(Observable.just(MOCKED_TRAVIS_BUILD_API));

    Observable<TravisBuild> observable = repository.get(REPO_ID, BUILD_ID);
    observable.subscribe(testSubscriber);

    testSubscriber.assertValue(MOCKED_TRAVIS_BUILD_API);
  }

  @Test
  public void ShouldReturnValidFromCache_whenCacheIsOkAndApiIsOk() throws Exception {
    when(cache.get(REPO_ID, BUILD_ID)).thenReturn(Observable.just(MOCKED_TRAVIS_BUILD_CACHE));
    when(api.get(REPO_ID, BUILD_ID)).thenReturn(Observable.just(MOCKED_TRAVIS_BUILD_API));

    Observable<TravisBuild> observable = repository.get(REPO_ID, BUILD_ID);
    observable.first().subscribe(testSubscriber);

    testSubscriber.assertValue(MOCKED_TRAVIS_BUILD_CACHE);
  }

  @Test
  public void ShouldReturnValidFromCacheAndApi_whenCacheIsOkAndApiIsOk() throws Exception {
    when(cache.get(REPO_ID, BUILD_ID)).thenReturn(Observable.just(MOCKED_TRAVIS_BUILD_CACHE));
    when(api.get(REPO_ID, BUILD_ID)).thenReturn(Observable.just(MOCKED_TRAVIS_BUILD_API));

    Observable<TravisBuild> observable = repository.get(REPO_ID, BUILD_ID);
    observable.subscribe(testSubscriber);

    testSubscriber.assertValues(MOCKED_TRAVIS_BUILD_CACHE, MOCKED_TRAVIS_BUILD_API);
  }
}