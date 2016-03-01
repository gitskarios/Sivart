package com.alorma.travisdk.bean.utils;

import com.alorma.travisdk.bean.response.BuildState;
import com.alorma.travisdk.bean.response.RepositoryResponse;

public enum StateColor {
  GREEN,
  YELLOW,
  RED,
  GRAY;

  public static StateColor fromState(@RepositoryResponse.State String state) {
    if (BuildState.FAILED.equalsIgnoreCase(state)) {
      return RED;
    } else if (BuildState.ERRORED.equalsIgnoreCase(state)) {
      return RED;
    } else if (BuildState.CREATED.equalsIgnoreCase(state)) {
      return YELLOW;
    } else if (BuildState.QUEUED.equalsIgnoreCase(state)) {
      return YELLOW;
    } else if (BuildState.STARTED.equalsIgnoreCase(state)) {
      return YELLOW;
    } else if (BuildState.PASSED.equalsIgnoreCase(state)) {
      return GREEN;
    }
    return GRAY;
  }
}
