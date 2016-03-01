package com.alorma.travisdk.bean.response;

import com.google.gson.annotations.SerializedName;

public class TravisCommitResponse {
  private long id;
  private String sha;
  private String branch;
  @SerializedName("branch_is_default") private boolean branchIsDefault;
  private String message;
  @SerializedName("committed_at") private String committedAt;
  @SerializedName("author_name") private String authorName;
  @SerializedName("author_email") private String authorEmail;
  @SerializedName("committer_name") private String committerName;
  @SerializedName("committer_email") private String committerEmail;
  @SerializedName("compare_url") private String compareUrl;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getSha() {
    return sha;
  }

  public void setSha(String sha) {
    this.sha = sha;
  }

  public String getBranch() {
    return branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

  public boolean isBranchIsDefault() {
    return branchIsDefault;
  }

  public void setBranchIsDefault(boolean branchIsDefault) {
    this.branchIsDefault = branchIsDefault;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getCommittedAt() {
    return committedAt;
  }

  public void setCommittedAt(String committedAt) {
    this.committedAt = committedAt;
  }

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public String getAuthorEmail() {
    return authorEmail;
  }

  public void setAuthorEmail(String authorEmail) {
    this.authorEmail = authorEmail;
  }

  public String getCommitterName() {
    return committerName;
  }

  public void setCommitterName(String committerName) {
    this.committerName = committerName;
  }

  public String getCommitterEmail() {
    return committerEmail;
  }

  public void setCommitterEmail(String committerEmail) {
    this.committerEmail = committerEmail;
  }

  public String getCompareUrl() {
    return compareUrl;
  }

  public void setCompareUrl(String compareUrl) {
    this.compareUrl = compareUrl;
  }
}
