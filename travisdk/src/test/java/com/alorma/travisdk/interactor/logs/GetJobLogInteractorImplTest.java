package com.alorma.travisdk.interactor.logs;

import com.alorma.travisdk.bean.response.TravisLogResponse;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.interactor.accounts.ActiveCredentialRepository;
import com.alorma.travisdk.interactor.accounts.ActiveCredentialRepositoryImpl;
import com.alorma.travisdk.repository.logs.GetLogRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class GetJobLogInteractorImplTest {

  @Mock GetLogRepository repository;
  private GetJobLogInteractor interactor;

  @Mock TravisLogResponse logResponse;
  private ActiveCredentialRepository credentialRepository;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    credentialRepository = spy(ActiveCredentialRepositoryImpl.getInstance());
    interactor = new GetJobLogInteractorImpl(repository, credentialRepository);
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

  @Test
  public void ShouldThrowExceptionOnError_whenDataFromRepositoryIsOk() throws Exception {

    when(credentialRepository.getCredential()).thenReturn(
        Observable.error(new IllegalStateException()));

    Observable<String> observable = interactor.getLogObservable(0);
    TestSubscriber<String> testSubscriber = new TestSubscriber<>();
    observable.subscribe(testSubscriber);

    testSubscriber.assertError(IllegalStateException.class);
  }

  @Test
  public void ShouldReturnResponseValid_whenDataFromRepositoryIsOk() throws Exception {
    credentialRepository.set(new Credential());

    GetJobLogInteractor spy = spy(interactor);
    when(spy.getLog(anyLong())).thenReturn("AAA");

    Observable<String> observable = spy.getLogObservable(anyLong());
    TestSubscriber<String> testSubscriber = new TestSubscriber<>();
    observable.subscribe(testSubscriber);

    testSubscriber.assertValue("AAA");
  }
}