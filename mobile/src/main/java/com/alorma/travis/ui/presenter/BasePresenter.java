package com.alorma.travis.ui.presenter;

import com.alorma.gitskarios.core.client.TokenProvider;
import com.alorma.gitskarios.core.client.UrlProvider;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BasePresenter {

  public void setupGithubToken(String token) {
    TokenProvider.setTokenProviderInstance(() -> token);
  }
  public void setupGithubUrl(String githubUrl) {
    if (githubUrl != null) {
      UrlProvider.setUrlProviderInstance(() -> githubUrl);
    }
  }

}
