package com.alorma.travis.ui.presenter.accounts;

import android.content.Context;
import android.util.Pair;
import com.alorma.travis.ui.presenter.BasePresenter;
import com.alorma.travis.ui.presenter.credential.AndroidAccountsCredentialsDatasource;
import com.alorma.travis.ui.presenter.utils.retrofit.GithubRetrofit;
import com.alorma.travisdk.bean.response.GithubAccount;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.datasource.accounts.AccountsDataSource;
import com.alorma.travisdk.datasource.accounts.cache.CacheAccountsDataSource;
import com.alorma.travisdk.interactor.accounts.GetAccountsInteractor;
import com.alorma.travisdk.interactor.accounts.GetAccountsInteractorImpl;
import com.alorma.travisdk.interactor.credentials.GetCredentialsInteractor;
import com.alorma.travisdk.interactor.credentials.GetCredentialsInteractorImpl;
import com.alorma.travisdk.repository.accounts.GetAccountsRepository;
import com.alorma.travisdk.repository.accounts.GetAccountsRepositoryImpl;
import com.alorma.travisdk.repository.credentials.CredentialsRepositoryImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CredentialsPresenter extends BasePresenter {

  private CredentialsPresenterCallback callbackNull =
      new CredentialsPresenterCallback.NullCallback();
  private CredentialsPresenterCallback callback = callbackNull;

  private final AndroidAccountsCredentialsDatasource cacheDataSource;
  private Subscription subscription;

  public CredentialsPresenter(Context context) {
    cacheDataSource = new AndroidAccountsCredentialsDatasource(context);
  }

  public void start() {
    subscription = getCredentials().flatMap(Observable::from)
        .flatMap(credential -> getGithubAccounts(credential).subscribeOn(Schedulers.io()))
        .toList()
        .map(pairs -> {
          Map<Credential, List<GithubAccount>> credentialMap = new HashMap<>();
          for (Pair<Credential, List<GithubAccount>> pair : pairs) {
            credentialMap.put(pair.first, pair.second);
          }
          return credentialMap;
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(credentials -> {
          if (credentials.isEmpty()) {
            callback.showLogin();
          } else {
            callback.showListCredentials(credentials);
          }
        }, Throwable::printStackTrace);
  }

  private Observable<List<Credential>> getCredentials() {
    GetCredentialsInteractor credentialsInteractor =
        new GetCredentialsInteractorImpl(new CredentialsRepositoryImpl(cacheDataSource));

    return credentialsInteractor.getCredentialsObs()
        .subscribeOn(Schedulers.immediate())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(() -> callback.willLoadCredentials())
        .doOnCompleted(() -> callback.didLoadCredentials());
  }

  private Observable<Pair<Credential, List<GithubAccount>>> getGithubAccounts(
      Credential credential) {

    AccountsDataSource user = new GithubUserDataSource(new GithubRetrofit(), credential);
    AccountsDataSource orgs = new GithubOrgsDataSource(new GithubRetrofit(), credential);

    AccountsDataSource cache =
        new CacheAccountsDataSource(credential.getName(), credential.getToken());

    GetAccountsRepository repository = new GetAccountsRepositoryImpl(cache, user, orgs);
    GetAccountsInteractor accountsInteractor = new GetAccountsInteractorImpl(repository);
    return accountsInteractor.accounts().map(accounts -> new Pair<>(credential, accounts));
  }

  public void setCallback(CredentialsPresenterCallback callback) {
    this.callback = callback;
  }

  public void stop() {
    if (subscription != null) {
      subscription.unsubscribe();
    }
    callback = callbackNull;
  }

  public interface CredentialsPresenterCallback {
    void willLoadCredentials();

    void showLogin();

    void showListCredentials(Map<Credential, List<GithubAccount>> credentials);

    void didLoadCredentials();

    class NullCallback implements CredentialsPresenterCallback {

      @Override
      public void willLoadCredentials() {

      }

      @Override
      public void showLogin() {

      }

      @Override
      public void showListCredentials(Map<Credential, List<GithubAccount>> credentials) {

      }

      @Override
      public void didLoadCredentials() {

      }
    }
  }
}
