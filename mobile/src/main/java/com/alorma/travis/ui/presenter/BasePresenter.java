package com.alorma.travis.ui.presenter;

import com.alorma.gitskarios.core.client.TokenProvider;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BasePresenter {

  public void setupGithubToken(String token) {
    TokenProvider.setTokenProviderInstance(() -> token);
  }

}
