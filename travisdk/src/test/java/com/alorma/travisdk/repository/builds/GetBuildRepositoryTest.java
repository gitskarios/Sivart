package com.alorma.travisdk.repository.builds;

import com.alorma.travisdk.bean.response.TravisBuild;
import com.alorma.travisdk.datasource.builds.GetBuildDataSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetBuildRepositoryTest {

  private int REPO_ID = 23436881;
  private int BUILD_ID = 23436881;

  private GetBuildRepositoryImpl repository;

  @Mock GetBuildDataSource cache;
  @Mock GetBuildDataSource api;

  @Mock private TravisBuild MOCKED_TRAVIS_BUILD_CACHE;
  @Mock private TravisBuild MOCKED_TRAVIS_BUILD_API;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    repository = new GetBuildRepositoryImpl(cache, api);
  }

  @Test
  public void ShouldReturnNull_whenCacheIsNullAndApiIsNull() throws Exception {
    when(cache.get(REPO_ID, BUILD_ID)).thenReturn(null);
    when(api.get(REPO_ID, BUILD_ID)).thenReturn(null);

    TravisBuild travisBuild = repository.get(REPO_ID, BUILD_ID);

    assertThat(travisBuild).isNull();
  }

  @Test
  public void ShouldReturnNull_whenCacheIsEmptyAndApiIsOk() throws Exception {
    when(cache.get(REPO_ID, BUILD_ID)).thenReturn(null);
    when(api.get(REPO_ID, BUILD_ID)).thenReturn(MOCKED_TRAVIS_BUILD_API);

    TravisBuild travisBuild = repository.get(REPO_ID, BUILD_ID);

    assertThat(travisBuild).isEqualTo(MOCKED_TRAVIS_BUILD_API);
  }

  @Test
  public void ShouldReturnValid_whenCacheIsOkAndApiIsOk() throws Exception {
    when(cache.get(REPO_ID, BUILD_ID)).thenReturn(MOCKED_TRAVIS_BUILD_CACHE);
    when(api.get(REPO_ID, BUILD_ID)).thenReturn(MOCKED_TRAVIS_BUILD_API);

    TravisBuild travisBuild = repository.get(REPO_ID, BUILD_ID);

    assertThat(travisBuild).isEqualTo(MOCKED_TRAVIS_BUILD_CACHE);
  }

  @Test
  public void ShouldCallSave_whenCacheIsEmptyAndApiIsOk() throws Exception {
    when(cache.get(REPO_ID, BUILD_ID)).thenReturn(null);
    when(api.get(REPO_ID, BUILD_ID)).thenReturn(MOCKED_TRAVIS_BUILD_API);

    repository.get(REPO_ID, BUILD_ID);

    verify(cache).save(REPO_ID, BUILD_ID, MOCKED_TRAVIS_BUILD_API);

  }
}