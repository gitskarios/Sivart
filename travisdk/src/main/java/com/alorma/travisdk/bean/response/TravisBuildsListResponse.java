package com.alorma.travisdk.bean.response;

import java.util.List;

public class TravisBuildsListResponse {
  private List<TravisBuildResponse> builds;
  private List<TravisCommitResponse> commits;

  public List<TravisBuildResponse> getBuilds() {
    return builds;
  }

  public void setBuilds(List<TravisBuildResponse> builds) {
    this.builds = builds;
  }

  public List<TravisCommitResponse> getCommits() {
    return commits;
  }

  public void setCommits(List<TravisCommitResponse> commits) {
    this.commits = commits;
  }
}
