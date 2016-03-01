package com.alorma.travisdk.bean.response;

import java.util.List;

public class TravisBuild {
  private TravisBuildResponse build;
  private TravisCommitResponse commit;
  private List<TravisJobResponse> jobs;
  private List<TravisAnnotationResponse> annotations;

  public TravisBuildResponse getBuild() {
    return build;
  }

  public void setBuild(TravisBuildResponse build) {
    this.build = build;
  }

  public TravisCommitResponse getCommit() {
    return commit;
  }

  public void setCommit(TravisCommitResponse commit) {
    this.commit = commit;
  }

  public List<TravisJobResponse> getJobs() {
    return jobs;
  }

  public void setJobs(List<TravisJobResponse> jobs) {
    this.jobs = jobs;
  }

  public List<TravisAnnotationResponse> getAnnotations() {
    return annotations;
  }

  public void setAnnotations(List<TravisAnnotationResponse> annotations) {
    this.annotations = annotations;
  }

  @Override
  public String toString() {
    return "TravisBuild{" +
        "build=" + String.valueOf(build) +
        ", commit=" + commit +
        ", jobs=" + jobs +
        ", annotations=" + annotations +
        '}';
  }

  public boolean isBuildOk() {
    return build.getState() != null && build.getState().equalsIgnoreCase("passed");
  }

}
