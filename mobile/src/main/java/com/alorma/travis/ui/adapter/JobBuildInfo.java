package com.alorma.travis.ui.adapter;

import com.alorma.travisdk.bean.response.TravisJobResponse;

public class JobBuildInfo extends BuildInfo {

  private TravisJobResponse job;

  public TravisJobResponse getJob() {
    return job;
  }

  public void setJob(TravisJobResponse job) {
    this.job = job;
  }
}
