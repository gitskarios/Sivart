package com.alorma.travis.ui.presenter.login;

import android.content.Context;
import android.support.annotation.NonNull;
import com.alorma.github.sdk.bean.dto.response.User;
import com.alorma.github.sdk.services.user.GetAuthUserClient;
import com.alorma.travis.ui.presenter.BasePresenter;
import com.alorma.travis.ui.presenter.utils.RetrofitWrapper;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.datasource.login.CredentialsDataSource;
import com.alorma.travisdk.interactor.accounts.GetCredentialsInteractorImpl;
import com.alorma.travisdk.interactor.auth.AuthenticateInteractorImpl;
import com.alorma.travisdk.repository.auth.AuthenticationRepository;
import com.alorma.travisdk.repository.auth.AuthenticationRepositoryImpl;
import com.alorma.travisdk.repository.credentials.CredentialsRepositoryImpl;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter {

  private LoginPresenterCallback presenterCallbackNull = new LoginPresenterCallback.NullCallback();
  private LoginPresenterCallback presenterCallback = presenterCallbackNull;

  private GetCredentialsInteractorImpl credentialsInteractor;

  private final CredentialsDataSource cacheDataSource;
  private User githubUser;

  public LoginPresenter(Context context) {
    cacheDataSource = new AccountsCredentialsDatasource(context);
  }

  public void start() {
    initCredentialsInteractor();

    getCredentialsObservable().subscribeOn(Schedulers.immediate())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(() -> presenterCallback.willLoadCredentials())
        .doOnCompleted(() -> presenterCallback.didLoadCredentials())
        .subscribe(credentials -> {
          presenterCallback.didLoadCredentials();
          if (credentials.isEmpty()) {
            presenterCallback.showLoginForm();
          } else if (credentials.size() == 1) {
            presenterCallback.loadCredential(credentials.get(0));
          } else {
            presenterCallback.showListCredentials(credentials);
          }
        }, Throwable::printStackTrace);
  }

  @NonNull
  private Observable<List<Credential>> getCredentialsObservable() {
    return Observable.create((Observable.OnSubscribe<List<Credential>>) subscriber -> {
      if (!subscriber.isUnsubscribed()) {
        subscriber.onStart();
        subscriber.onNext(credentialsInteractor.getCredentials());
        subscriber.onCompleted();
      }
    });
  }

  private void initCredentialsInteractor() {
    credentialsInteractor =
        new GetCredentialsInteractorImpl(new CredentialsRepositoryImpl(cacheDataSource));
  }

  public void stop() {
    presenterCallback = presenterCallbackNull;
  }

  public void setCallback(LoginPresenterCallback presenterCallback) {
    this.presenterCallback = presenterCallback;
  }

  public void githubLogin(String ghToken, String url) {
    GetAuthUserClient authUserClient = new GetAuthUserClient(ghToken);
    Observable<User> userObservable = authUserClient.observable()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());

    userObservable.doOnNext(user1 -> this.githubUser = user1).subscribe(user -> {
      confirmLogin(ghToken, url, null);
    }, Throwable::printStackTrace);
  }

  public void githubLoginEnterprise(String ghToken, String url, String enterpriseUrl) {
    setupGithubUrl(enterpriseUrl);
    GetAuthUserClient authUserClient = new GetAuthUserClient(ghToken);
    Observable<User> userObservable = authUserClient.observable()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());

    userObservable.doOnNext(user1 -> this.githubUser = user1).subscribe(user -> {
      confirmLogin(ghToken, url, enterpriseUrl);
    }, Throwable::printStackTrace);
  }

  public void confirmLogin(String ghToken, String url, String githubUrl) {
    RetrofitWrapper retrofitWrapper = new RetrofitWrapper();
    CredentialsDataSource apiDatasource = new ApiCredentialsDataSource(retrofitWrapper);
    AuthenticationRepository authenticationRepository =
        new AuthenticationRepositoryImpl(cacheDataSource, apiDatasource);
    AuthenticateInteractorImpl authenticateInteractor =
        new AuthenticateInteractorImpl(authenticationRepository);

    Observable<Credential> observable = Observable.create(subscriber -> {
      if (!subscriber.isUnsubscribed()) {
        subscriber.onStart();
        Credential credential = new Credential();
        credential.setGithubToken(ghToken);
        credential.setTravisUrl(url);
        credential.setGithubUrl(githubUrl);
        credential.setName(githubUser.login);
        credential.setAvatar(githubUser.avatar_url);

        try {
          subscriber.onNext(authenticateInteractor.authenticate(credential));
          subscriber.onCompleted();
        } catch (Exception e) {
          subscriber.onError(e);
        }
      }
    });

    observable.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext(credential -> start())
        .subscribe(credential1 -> {
        }, Throwable::printStackTrace);
  }

  public interface LoginPresenterCallback {
    void willLoadCredentials();

    void showLoginForm();

    void showListCredentials(List<Credential> credentials);

    void didLoadCredentials();

    void loadCredential(Credential credential);

    class NullCallback implements LoginPresenterCallback {

      @Override
      public void willLoadCredentials() {

      }

      @Override
      public void showLoginForm() {

      }

      @Override
      public void showListCredentials(List<Credential> credentials) {

      }

      @Override
      public void didLoadCredentials() {

      }

      @Override
      public void loadCredential(Credential credential) {

      }
    }
  }
}
