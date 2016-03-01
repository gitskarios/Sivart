package com.alorma.travisdk.bean.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class Credential implements Parcelable {
  private String name;
  private String token;
  private String githubToken;
  private String url;
  private String avatar;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  @Override
  public String toString() {
    return "Credential{" +
        "name='" + name + '\'' +
        ", token='" + token + '\'' +
        ", githubToken='" + githubToken + '\'' +
        ", url='" + url + '\'' +
        ", avatar='" + avatar + '\'' +
        '}';
  }

  public String getGithubToken() {
    return githubToken;
  }

  public void setGithubToken(String githubToken) {
    this.githubToken = githubToken;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.name);
    dest.writeString(this.token);
    dest.writeString(this.githubToken);
    dest.writeString(this.url);
    dest.writeString(this.avatar);
  }

  public Credential() {
  }

  protected Credential(Parcel in) {
    this.name = in.readString();
    this.token = in.readString();
    this.githubToken = in.readString();
    this.url = in.readString();
    this.avatar = in.readString();
  }

  public static final Parcelable.Creator<Credential> CREATOR =
      new Parcelable.Creator<Credential>() {
        public Credential createFromParcel(Parcel source) {
          return new Credential(source);
        }

        public Credential[] newArray(int size) {
          return new Credential[size];
        }
      };
}
