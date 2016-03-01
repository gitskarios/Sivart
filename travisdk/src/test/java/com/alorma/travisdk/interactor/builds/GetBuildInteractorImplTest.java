package com.alorma.travisdk.interactor.builds;

import com.alorma.travisdk.bean.response.TravisBuild;
import com.alorma.travisdk.bean.response.TravisBuildResponse;
import com.alorma.travisdk.repository.builds.GetBuildRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Java6Assertions.assertThat;
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

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    interactor = new GetBuildInteractorImpl(buildRepository);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenNegativelRepoIdIsPassed() throws Exception {
    interactor.get(REPO_NEGATIVE_ID, BUILD_ID);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenZeroRepoIdIsPassed() throws Exception {
    interactor.get(REPO_ZERO_ID, BUILD_ID);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenNegativelBuildIdIsPassed() throws Exception {
    interactor.get(REPO_ID, NEGATIVE_BUILD_ID);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenZeroBuildIdIsPassed() throws Exception {
    interactor.get(REPO_ID, ZERO_BUILD_ID);
  }

  @Test
  public void shouldGetTravisBuild_whenEmptyBuildIdIsPassed() throws Exception {
    TravisBuild travisBuild = new TravisBuild();
    travisBuild.setBuild(new TravisBuildResponse());
    travisBuild.getBuild().setId(BUILD_ID);

    when(buildRepository.get(REPO_ID, BUILD_ID)).thenReturn(travisBuild);

    TravisBuild travisBuildReceived = interactor.get(REPO_ID, BUILD_ID);

    assertThat(travisBuildReceived).isEqualTo(travisBuild);
    assertThat(travisBuildReceived.getBuild().getId()).isEqualTo(travisBuild.getBuild().getId());
  }
}