package com.alorma.travisdk.bean.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class RepositoryResponse implements Parcelable {
  public long id;
  public String slug;
  public String description;
  @SerializedName("last_build_id") public long lastBuildId;
  @SerializedName("last_build_number") public String lastBuildNumber;

  @StringDef({
      BuildState.CREATED, BuildState.QUEUED, BuildState.STARTED, BuildState.PASSED,
      BuildState.FAILED, BuildState.ERRORED, BuildState.CANCELED, BuildState.READY
  }) public @interface State {

  }

  @SerializedName("last_build_state") @RepositoryResponse.State public String lastBuildState;
  @SerializedName("last_build_started_at") public Date lastBuildStarted;
  @SerializedName("last_build_finished_at") public Date lastBuildFinished;
  @SerializedName("github_language") public String githubLanguage;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public long getLastBuildId() {
    return lastBuildId;
  }

  public void setLastBuildId(long lastBuildId) {
    this.lastBuildId = lastBuildId;
  }

  public String getLastBuildNumber() {
    return lastBuildNumber;
  }

  public void setLastBuildNumber(String lastBuildNumber) {
    this.lastBuildNumber = lastBuildNumber;
  }

  @State
  public String getLastBuildState() {
    return lastBuildState;
  }

  public void setLastBuildState(@State String lastBuildState) {
    this.lastBuildState = lastBuildState;
  }

  public Date getLastBuildStarted() {
    return lastBuildStarted;
  }

  public void setLastBuildStarted(Date lastBuildStarted) {
    this.lastBuildStarted = lastBuildStarted;
  }

  public Date getLastBuildFinished() {
    return lastBuildFinished;
  }

  public void setLastBuildFinished(Date lastBuildFinished) {
    this.lastBuildFinished = lastBuildFinished;
  }

  public String getGithubLanguage() {
    return githubLanguage;
  }

  public void setGithubLanguage(String githubLanguage) {
    this.githubLanguage = githubLanguage;
  }

  public boolean isBuildOk() {
    return lastBuildState != null && lastBuildState.equalsIgnoreCase("passed");
  }

  public String getOwner() {
    return slug.split("/")[0];
  }

  public String getRepo() {
    return slug.split("/")[1];
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(this.id);
    dest.writeString(this.slug);
    dest.writeString(this.description);
    dest.writeLong(this.lastBuildId);
    dest.writeString(this.lastBuildNumber);
    dest.writeString(this.lastBuildState);
    dest.writeLong(lastBuildStarted != null ? lastBuildStarted.getTime() : -1);
    dest.writeLong(lastBuildFinished != null ? lastBuildFinished.getTime() : -1);
    dest.writeString(this.githubLanguage);
  }

  public RepositoryResponse() {
  }

  protected RepositoryResponse(Parcel in) {
    this.id = in.readLong();
    this.slug = in.readString();
    this.description = in.readString();
    this.lastBuildId = in.readLong();
    this.lastBuildNumber = in.readString();
    this.lastBuildState = in.readString();
    long tmpLastBuildStarted = in.readLong();
    this.lastBuildStarted = tmpLastBuildStarted == -1 ? null : new Date(tmpLastBuildStarted);
    long tmpLastBuildFinished = in.readLong();
    this.lastBuildFinished = tmpLastBuildFinished == -1 ? null : new Date(tmpLastBuildFinished);
    this.githubLanguage = in.readString();
  }

  public static final Parcelable.Creator<RepositoryResponse> CREATOR =
      new Parcelable.Creator<RepositoryResponse>() {
        public RepositoryResponse createFromParcel(Parcel source) {
          return new RepositoryResponse(source);
        }

        public RepositoryResponse[] newArray(int size) {
          return new RepositoryResponse[size];
        }
      };
}
