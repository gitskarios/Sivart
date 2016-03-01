package com.alorma.travisdk.bean.response;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryResponseBuildTest {

  private RepositoryResponse repositoryResponse;

  @Before
  public void setUp() throws Exception {
    repositoryResponse = new RepositoryResponse();
  }

  @Test
  public void givenCreatedShouldBeFalse() throws Exception {
    repositoryResponse.setLastBuildState(BuildState.CREATED);

    assertThat(repositoryResponse.isBuildOk()).isFalse();
  }

  @Test
  public void givenQueuedShouldBeFalse() throws Exception {
    repositoryResponse.setLastBuildState(BuildState.QUEUED);

    assertThat(repositoryResponse.isBuildOk()).isFalse();
  }

  @Test
  public void givenStartedShouldBeFalse() throws Exception {
    repositoryResponse.setLastBuildState(BuildState.STARTED);

    assertThat(repositoryResponse.isBuildOk()).isFalse();
  }

  @Test
  public void givenPassedShouldBeTrue() throws Exception {
    repositoryResponse.setLastBuildState(BuildState.PASSED);

    assertThat(repositoryResponse.isBuildOk()).isTrue();
  }

  @Test
  public void givenFailedShouldBeFalse() throws Exception {
    repositoryResponse.setLastBuildState(BuildState.FAILED);

    assertThat(repositoryResponse.isBuildOk()).isFalse();
  }

  @Test
  public void givenErroredShouldBeFalse() throws Exception {
    repositoryResponse.setLastBuildState(BuildState.ERRORED);

    assertThat(repositoryResponse.isBuildOk()).isFalse();
  }

  @Test
  public void givenCanceledShouldBeFalse() throws Exception {
    repositoryResponse.setLastBuildState(BuildState.CANCELED);

    assertThat(repositoryResponse.isBuildOk()).isFalse();
  }

  @Test
  public void givenReadyShouldBeFalse() throws Exception {
    repositoryResponse.setLastBuildState(BuildState.READY);

    assertThat(repositoryResponse.isBuildOk()).isFalse();
  }
}