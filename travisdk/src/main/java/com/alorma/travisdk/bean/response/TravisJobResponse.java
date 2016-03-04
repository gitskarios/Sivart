package com.alorma.travisdk.bean.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;
import com.google.gson.annotations.SerializedName;
import java.util.Arrays;

public class TravisJobResponse implements Parcelable {

  private long id;
  @SerializedName("repository_id") private long repositoryId;
  @SerializedName("build_id") private long buildId;
  @SerializedName("commit_id") private long commitId;
  @SerializedName("log_id") private long logId;

  @Override
  public String toString() {
    return "TravisJobResponse{" +
        "id=" + id +
        ", repositoryId=" + repositoryId +
        ", buildId=" + buildId +
        ", commitId=" + commitId +
        ", logId=" + logId +
        ", state='" + state + '\'' +
        ", number='" + number + '\'' +
        ", config=" + config +
        ", script='" + script + '\'' +
        ", startedAt='" + startedAt + '\'' +
        ", finishedAt='" + finishedAt + '\'' +
        ", queue='" + queue + '\'' +
        ", allowFailure=" + allowFailure +
        ", annotationIds=" + Arrays.toString(annotationIds) +
        ", tags=" + Arrays.toString(tags) +
        '}';
  }

  @StringDef({
      BuildState.CREATED, BuildState.QUEUED, BuildState.STARTED, BuildState.PASSED,
      BuildState.FAILED, BuildState.ERRORED, BuildState.CANCELED, BuildState.READY
  }) public @interface State {

  }

  private String state;
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

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(this.id);
    dest.writeLong(this.repositoryId);
    dest.writeLong(this.buildId);
    dest.writeLong(this.commitId);
    dest.writeLong(this.logId);
    dest.writeString(this.state);
    dest.writeString(this.number);
    // TODO dest.writeParcelable(this.config, flags);
    dest.writeString(this.script);
    dest.writeString(this.startedAt);
    dest.writeString(this.finishedAt);
    dest.writeString(this.queue);
    dest.writeByte(allowFailure ? (byte) 1 : (byte) 0);
    dest.writeStringArray(this.annotationIds);
    dest.writeStringArray(this.tags);
  }

  public TravisJobResponse() {
  }

  protected TravisJobResponse(Parcel in) {
    this.id = in.readLong();
    this.repositoryId = in.readLong();
    this.buildId = in.readLong();
    this.commitId = in.readLong();
    this.logId = in.readLong();
    this.state = in.readString();
    this.number = in.readString();
    // TODO this.config = in.readParcelable(Object.class.getClassLoader());
    this.script = in.readString();
    this.startedAt = in.readString();
    this.finishedAt = in.readString();
    this.queue = in.readString();
    this.allowFailure = in.readByte() != 0;
    this.annotationIds = in.createStringArray();
    this.tags = in.createStringArray();
  }

  public static final Parcelable.Creator<TravisJobResponse> CREATOR =
      new Parcelable.Creator<TravisJobResponse>() {
        public TravisJobResponse createFromParcel(Parcel source) {
          return new TravisJobResponse(source);
        }

        public TravisJobResponse[] newArray(int size) {
          return new TravisJobResponse[size];
        }
      };
}
