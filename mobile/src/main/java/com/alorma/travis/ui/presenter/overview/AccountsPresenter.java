package com.alorma.travis.ui.presenter.overview;

import android.support.annotation.NonNull;
import com.alorma.github.sdk.bean.dto.response.Organization;
import com.alorma.github.sdk.bean.dto.response.User;
import com.alorma.github.sdk.services.orgs.GetOrgsClient;
import com.alorma.github.sdk.services.user.RequestUserClient;
import com.alorma.travis.ui.presenter.repositories.RepositoriesPresenter;
import com.alorma.travisdk.bean.utils.Credential;
import java.util.List;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AccountsPresenter extends RepositoriesPresenter {

  private AccountsPresenterCallback accountsPresenterCallbackNull =
      new AccountsPresenterCallback.NullView();
  private AccountsPresenterCallback accountsPresenterCallback = accountsPresenterCallbackNull;

  private Credential credential;
  private Subscription subscriptionOrgs;
  private Subscription subscriptionUser;

  public AccountsPresenter(Credential credential) {
    super();
    this.credential = credential;
  }

  public void start() {

    setupGithubToken(credential.getGithubToken());
    setupGithubUrl(credential.getGithubUrl());

    subscriptionUser = getUserCredentials().subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(user -> {
          accountsPresenterCallback.userLoaded(user);
        }, Throwable::printStackTrace);

    subscriptionOrgs = loadOrganizations().subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .toList()
        .doOnSubscribe(() -> accountsPresenterCallback.willLoadOrganizations())
        .doOnCompleted(() -> accountsPresenterCallback.didLoadOrganizations())
        .subscribe(orgs -> {
          accountsPresenterCallback.organizationsLoaded(orgs);
        }, Throwable::printStackTrace);
  }

  @NonNull
  private Observable<User> getUserCredentials() {
    return new RequestUserClient(credential.getName()).observable()
        .doOnError(throwable -> {

        });
  }

  private Observable<Organization> loadOrganizations() {
    GetOrgsClient orgsClient = new GetOrgsClient(credential.getName());
    return orgsClient.observable()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .map(o -> o.first)
        .flatMap(Observable::from);
  }

  public void setAccountsCallback(AccountsPresenterCallback accountsPresenterCallback) {
    this.accountsPresenterCallback = accountsPresenterCallback;
  }

  public void stop() {
    if (subscriptionUser != null) {
      subscriptionUser.unsubscribe();
      subscriptionUser = null;
    }
    if (subscriptionOrgs != null) {
      subscriptionOrgs.unsubscribe();
      subscriptionOrgs = null;
    }
    this.accountsPresenterCallback = accountsPresenterCallbackNull;
  }

  public interface AccountsPresenterCallback {
    void willLoadOrganizations();

    void userLoaded(User user);

    void organizationsLoaded(List<Organization> organization);

    void didLoadOrganizations();

    class NullView implements AccountsPresenterCallback {

      @Override
      public void willLoadOrganizations() {

      }

      @Override
      public void organizationsLoaded(List<Organization> organization) {

      }

      @Override
      public void didLoadOrganizations() {

      }

      @Override
      public void userLoaded(User user) {

      }
    }
  }
}
