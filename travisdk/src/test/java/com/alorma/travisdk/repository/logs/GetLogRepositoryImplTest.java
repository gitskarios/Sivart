package com.alorma.travisdk.repository.logs;

import com.alorma.travisdk.bean.response.TravisLogResponse;
import com.alorma.travisdk.datasource.logs.GetLogDataSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class GetLogRepositoryImplTest {

  @Mock GetLogDataSource cache;
  @Mock GetLogDataSource api;

  private GetLogRepository repository;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    repository = new GetLogRepositoryImpl(cache, api);
  }

  @Test public void ShouldReturnNull_whenCacheIsNullAndApiIsNull() throws Exception{
    when(cache.getLog(anyLong())).thenReturn(null);
    when(api.getLog(anyLong())).thenReturn(null);

    String log = repository.getLog(anyLong());

    assertThat(log).isNull();
  }

  @Test public void ShouldReturnCached_whenCacheIsValidAndApiIsNull() throws Exception{
    when(cache.getLog(anyLong())).thenReturn("response");
    when(api.getLog(anyLong())).thenReturn(null);

    String log = repository.getLog(anyLong());

    assertThat(log).isEqualTo("response");
  }

  @Test public void ShouldReturnCached_whenCacheIsValidAndApiIsValid() throws Exception{
    when(cache.getLog(anyLong())).thenReturn("response");
    when(api.getLog(anyLong())).thenReturn("response2");

    String log = repository.getLog(anyLong());

    assertThat(log).isEqualTo("response");
  }

  @Test public void ShouldReturnCached_whenCacheIsInvalidAndApiIsValid() throws Exception{
    when(cache.getLog(anyLong())).thenReturn(null);
    when(api.getLog(anyLong())).thenReturn("response");

    String log = repository.getLog(anyLong());

    assertThat(log).isEqualTo("response");
  }
}