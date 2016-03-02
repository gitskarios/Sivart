package com.alorma.travisdk.bean.response;

import android.support.annotation.StringDef;
import com.google.gson.annotations.SerializedName;

public class TravisJobResponse {

  private long id;
  @SerializedName("repository_id") private long repositoryId;
  @SerializedName("build_id") private long buildId;
  @SerializedName("commit_id") private long commitId;
  @SerializedName("log_id") private long logId;

  @StringDef({
      BuildState.CREATED, BuildState.QUEUED, BuildState.STARTED, BuildState.PASSED,
      BuildState.FAILED, BuildState.ERRORED, BuildState.CANCELED, BuildState.READY
  }) public @interface State {

  }

  @TravisJobResponse.State private String state;
  private String number;
  private Object config;
  private String script;

  @SerializedName("started_at") private String startedAt;

  @SerializedName("finished_at") private String finishedAt;

  private String queue;

  @SerializedName("allow_failure") private boolean allowFailure;

  @SerializedName("annotation_ids") private String[] annotationIds;

  private String[] tags;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getRepositoryId() {
    return repositoryId;
  }

  public void setRepositoryId(long repositoryId) {
    this.repositoryId = repositoryId;
  }

  public long getBuildId() {
    return buildId;
  }

  public void setBuildId(long buildId) {
    this.buildId = buildId;
  }

  public long getCommitId() {
    return commitId;
  }

  public void setCommitId(long commitId) {
    this.commitId = commitId;
  }

  public long getLogId() {
    return logId;
  }

  public void setLogId(long logId) {
    this.logId = logId;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public Object getConfig() {
    return config;
  }

  public void setConfig(Object config) {
    this.config = config;
  }

  public String getScript() {
    return script;
  }

  public void setScript(String script) {
    this.script = script;
  }

  public String getStartedAt() {
    return startedAt;
  }

  public void setStartedAt(String startedAt) {
    this.startedAt = startedAt;
  }

  public String getFinishedAt() {
    return finishedAt;
  }

  public void setFinishedAt(String finishedAt) {
    this.finishedAt = finishedAt;
  }

  public String getQueue() {
    return queue;
  }

  public void setQueue(String queue) {
    this.queue = queue;
  }

  public boolean isAllowFailure() {
    return allowFailure;
  }

  public void setAllowFailure(boolean allowFailure) {
    this.allowFailure = allowFailure;
  }

  public String[] getAnnotationIds() {
    return annotationIds;
  }

  public void setAnnotationIds(String[] annotationIds) {
    this.annotationIds = annotationIds;
  }

  public String[] getTags() {
    return tags;
  }

  public void setTags(String[] tags) {
    this.tags = tags;
  }

  public boolean isJobOk() {
    return state != null && state.equalsIgnoreCase("passed");
  }
}
