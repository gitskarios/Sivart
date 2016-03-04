package com.alorma.travisdk.interactor.logs;

import com.alorma.travisdk.bean.response.TravisLogResponse;
import com.alorma.travisdk.repository.logs.GetLogRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class GetJobLogInteractorImplTest {

  @Mock GetLogRepository repository;
  private GetJobLogInteractor interactor;

  @Mock TravisLogResponse logResponse;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    interactor = new GetJobLogInteractorImpl(repository);
  }

  @Test
  public void ShouldReturnNull_whenNoDataFromRepository() throws Exception {
    when(repository.getLog(anyLong())).thenReturn(null);

    String object = interactor.getLog(anyLong());

    assertThat(object).isNull();
  }

  @Test
  public void ShouldReturnResponse_whenDataFromRepositoryIsOk() throws Exception {
    when(repository.getLog(anyLong())).thenReturn("response");

    String object = interactor.getLog(anyLong());

    assertThat(object).isEqualTo("response");
  }
}