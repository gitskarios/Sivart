package com.alorma.travisdk.interactor.credentials;

import com.alorma.travisdk.bean.utils.Credential;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;

public class ActiveCredentialRepositoryImplTest {

  private ActiveCredentialRepository repository;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    repository = spy(ActiveCredentialRepositoryImpl.getInstance());
  }

  @Test(expected = IllegalStateException.class)
  public void ShouldThrowException_whenNoCredential() {

    repository.set(null);

    repository.get();
  }

  @Test
  public void ShouldReturn_whenCredentialWasSaved() {
    Credential credential = new Credential();
    repository.set(credential);

    Credential credential1 = repository.get();

    assertThat(credential1).isNotNull();
  }

  @Test
  public void ShouldThrowExceptionOnError_whenNoCredential() {
    repository.set(null);
    Observable<Credential> observable = repository.getCredential();
    TestSubscriber<Credential> testSubscriber = new TestSubscriber<>();

    observable.subscribe(testSubscriber);

    testSubscriber.assertError(IllegalStateException.class);
  }

  @Test
  public void ShouldReturnCredentialOnSubscribed_whenCredentialWasSaved() {
    Credential credential = new Credential();
    repository.set(credential);

    Observable<Credential> observable = repository.getCredential();
    TestSubscriber<Credential> testSubscriber = new TestSubscriber<>();
    observable.subscribe(testSubscriber);

    testSubscriber.assertValue(credential);
  }
}