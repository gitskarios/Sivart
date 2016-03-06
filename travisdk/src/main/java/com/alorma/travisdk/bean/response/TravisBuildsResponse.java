package com.alorma.travisdk.bean.response;

import java.util.List;

public class TravisBuildsResponse {
  private List<TravisBuildResponse> builds;

  public List<TravisBuildResponse> getBuilds() {
    return builds;
  }

  public void setBuilds(List<TravisBuildResponse> builds) {
    this.builds = builds;
  }
}
