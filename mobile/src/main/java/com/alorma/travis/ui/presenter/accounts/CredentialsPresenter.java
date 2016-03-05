package com.alorma.travis.ui.presenter.accounts;

import android.content.Context;
import com.alorma.travis.ui.presenter.BasePresenter;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.interactor.accounts.GetCredentialsInteractor;
import com.alorma.travisdk.interactor.accounts.GetCredentialsInteractorImpl;
import com.alorma.travisdk.repository.credentials.CredentialsRepositoryImpl;
import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CredentialsPresenter extends BasePresenter {

  private CredentialsPresenterCallback callbackNull =
      new CredentialsPresenterCallback.NullCallback();
  private CredentialsPresenterCallback callback = callbackNull;

  private final AccountsCredentialsDatasource cacheDataSource;
  private Subscription subscription;

  public CredentialsPresenter(Context context) {
    cacheDataSource = new AccountsCredentialsDatasource(context);
  }

  public void start() {
    GetCredentialsInteractor credentialsInteractor =
        new GetCredentialsInteractorImpl(new CredentialsRepositoryImpl(cacheDataSource));

    subscription = credentialsInteractor.getCredentialsObs()
        .subscribeOn(Schedulers.immediate())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(() -> callback.willLoadCredentials())
        .doOnCompleted(() -> callback.didLoadCredentials())
        .subscribe(credentials -> {
          if (credentials.isEmpty()) {
            callback.showLogin();
          } else {
            callback.showListCredentials(credentials);
          }
        }, Throwable::printStackTrace);
  }

  public void setCallback(CredentialsPresenterCallback callback) {
    this.callback = callback;
  }

  public  void stop() {
    if (subscription != null) {
     subscription.unsubscribe();
    }
    callback = callbackNull;
  }

  public interface CredentialsPresenterCallback {
    void willLoadCredentials();

    void showLogin();

    void showListCredentials(List<Credential> credentials);

    void didLoadCredentials();

    class NullCallback implements CredentialsPresenterCallback {

      @Override
      public void willLoadCredentials() {

      }

      @Override
      public void showLogin() {

      }

      @Override
      public void showListCredentials(List<Credential> credentials) {

      }

      @Override
      public void didLoadCredentials() {

      }
    }
  }
}
