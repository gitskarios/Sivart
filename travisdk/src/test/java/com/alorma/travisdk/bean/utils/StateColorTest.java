package com.alorma.travisdk.bean.utils;

import com.alorma.travisdk.bean.response.BuildState;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StateColorTest {

  @Test
  public void givenCreatedShouldBeYellow() throws Exception {
    assertThat(StateColor.fromState(BuildState.CREATED)).isEqualTo(StateColor.YELLOW);
  }

  @Test
  public void givenQueuedShouldBeYellow() throws Exception {
    assertThat(StateColor.fromState(BuildState.QUEUED)).isEqualTo(StateColor.YELLOW);
  }

  @Test
  public void givenStartedShouldBeYellow() throws Exception {
    assertThat(StateColor.fromState(BuildState.STARTED)).isEqualTo(StateColor.YELLOW);
  }

  @Test
  public void givenPassedShouldBeGreen() throws Exception {
    assertThat(StateColor.fromState(BuildState.PASSED)).isEqualTo(StateColor.GREEN);
  }

  @Test
  public void givenFailedShouldBeRed() throws Exception {
    assertThat(StateColor.fromState(BuildState.FAILED)).isEqualTo(StateColor.RED);
  }

  @Test
  public void givenErroredShouldBeRed() throws Exception {
    assertThat(StateColor.fromState(BuildState.ERRORED)).isEqualTo(StateColor.RED);
  }

  @Test
  public void givenCanceledShouldBeGray() throws Exception {
    assertThat(StateColor.fromState(BuildState.CANCELED)).isEqualTo(StateColor.GRAY);
  }

  @Test
  public void givenReadyShouldBeGray() throws Exception {
    assertThat(StateColor.fromState(BuildState.READY)).isEqualTo(StateColor.GRAY);
  }
}