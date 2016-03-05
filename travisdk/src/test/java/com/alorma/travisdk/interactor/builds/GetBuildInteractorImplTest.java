package com.alorma.travisdk.interactor.builds;

import com.alorma.travisdk.bean.response.TravisBuild;
import com.alorma.travisdk.bean.response.TravisBuildResponse;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.interactor.accounts.ActiveCredentialRepository;
import com.alorma.travisdk.interactor.accounts.ActiveCredentialRepositoryImpl;
import com.alorma.travisdk.repository.builds.GetBuildRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.when;

public class GetBuildInteractorImplTest {

  @Mock GetBuildRepository buildRepository;

  private GetBuildInteractorImpl interactor;

  private int REPO_NEGATIVE_ID = -1;
  private int REPO_ZERO_ID = 0;
  private int REPO_ID = 23436881;

  private int NEGATIVE_BUILD_ID = -1;
  private int ZERO_BUILD_ID = 0;
  private int BUILD_ID = 23436881;
  private TestSubscriber<TravisBuild> testSubscriber;
  private ActiveCredentialRepository activeCredentialRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    activeCredentialRepository = ActiveCredentialRepositoryImpl.getInstance();

    interactor = new GetBuildInteractorImpl(buildRepository, activeCredentialRepository);

    testSubscriber = new TestSubscriber<>();
  }

  @Test
  public void shouldThrowException_whenNegativelRepoIdIsPassed() {
    Observable<TravisBuild> observable = interactor.get(REPO_NEGATIVE_ID, BUILD_ID);
    observable.subscribe(testSubscriber);
    testSubscriber.assertError(IllegalArgumentException.class);
  }

  @Test
  public void shouldThrowException_whenZeroRepoIdIsPassed() {
    Observable<TravisBuild> observable = interactor.get(REPO_ZERO_ID, BUILD_ID);
    observable.subscribe(testSubscriber);
    testSubscriber.assertError(IllegalArgumentException.class);
  }

  @Test
  public void shouldThrowException_whenNegativelBuildIdIsPassed() {
    Observable<TravisBuild> observable = interactor.get(REPO_ID, NEGATIVE_BUILD_ID);
    observable.subscribe(testSubscriber);
    testSubscriber.assertError(IllegalArgumentException.class);
  }

  @Test
  public void shouldThrowException_whenZeroBuildIdIsPassed() {
    Observable<TravisBuild> observable = interactor.get(REPO_ID, ZERO_BUILD_ID);
    observable.subscribe(testSubscriber);
    testSubscriber.assertError(IllegalArgumentException.class);
  }

  @Test
  public void shouldGetTravisBuild_whenEmptyBuildIdIsPassed() {
    TravisBuild travisBuild = new TravisBuild();
    travisBuild.setBuild(new TravisBuildResponse());
    travisBuild.getBuild().setId(BUILD_ID);

    activeCredentialRepository.set(new Credential());

    when(buildRepository.get(REPO_ID, BUILD_ID)).thenReturn(Observable.just(travisBuild));

    Observable<TravisBuild> observable = interactor.get(REPO_ID, BUILD_ID);
    observable.subscribe(testSubscriber);

    testSubscriber.assertValue(travisBuild);
  }
}