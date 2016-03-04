package com.alorma.travis.ui.presenter.repositories;

import com.alorma.travis.ui.presenter.BasePresenter;
import com.alorma.travis.ui.presenter.overview.ApiTravisRepositoriesDataSource;
import com.alorma.travis.ui.presenter.utils.RetrofitWrapper;
import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.datasource.repos.TravisRepositoriesDataSource;
import com.alorma.travisdk.datasource.repos.cache.CacheTravisRepositoriesDataSource;
import com.alorma.travisdk.interactor.repos.GetTravisRepositoriesInteractorImpl;
import com.alorma.travisdk.repository.repos.TravisRepositoriesRepository;
import com.alorma.travisdk.repository.repos.TravisRepositoriesRepositoryImpl;
import java.util.List;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RepositoriesPresenter extends BasePresenter {

  private RepositoriesPresenterCallback overviewPresenterCallbackNull =
      new RepositoriesPresenterCallback.NullView();
  private RepositoriesPresenterCallback overviewPresenterCallback = overviewPresenterCallbackNull;

  private Credential credential;
  private Subscription subscription;

  public RepositoriesPresenter(Credential credential) {
    setupGithubUrl(credential.getGithubUrl());
    setupGithubToken(credential.getGithubToken());
    this.credential = credential;
  }

  public void loadRepositories(String name) {
    subscription = loadRepositories(name, credential.getTravisUrl(), credential.getToken()).observeOn(
        AndroidSchedulers.mainThread())
        .doOnSubscribe(() -> overviewPresenterCallback.willLoadRepositories())
        .doOnCompleted(() -> overviewPresenterCallback.didLoadRepositories())
        .subscribe(list -> {
          overviewPresenterCallback.repositoriesLoaded(list);
        }, Throwable::printStackTrace);
  }

  private Observable<List<RepositoryResponse>> loadRepositories(
      String owner, String url, String token) {
    TravisRepositoriesDataSource cache = new CacheTravisRepositoriesDataSource();
    TravisRepositoriesDataSource api =
        new ApiTravisRepositoriesDataSource(new RetrofitWrapper(), url, token);

    TravisRepositoriesRepository travisRepository =
        new TravisRepositoriesRepositoryImpl(cache, api);

    GetTravisRepositoriesInteractorImpl travisRepositoriesInteractor =
        new GetTravisRepositoriesInteractorImpl(travisRepository);

    Observable<List<RepositoryResponse>> listObservable = Observable.create(
        (Observable.OnSubscribe<List<RepositoryResponse>>) subscriber -> {
          if (!subscriber.isUnsubscribed()) {
            subscriber.onStart();

            try {
              List<RepositoryResponse> repos =
                  travisRepositoriesInteractor.getRepos(owner, true);

              subscriber.onNext(repos);
              subscriber.onCompleted();
            } catch (Exception e) {
              subscriber.onError(e);
            }
          }
        });

    listObservable = listObservable.subscribeOn(Schedulers.newThread());

    return listObservable;
  }

  public void setRepositoriesCallback(RepositoriesPresenterCallback overviewPresenterCallback) {
    this.overviewPresenterCallback = overviewPresenterCallback;
  }

  public void stop() {
    if (subscription != null) {
      subscription.unsubscribe();
      subscription = null;
    }
    this.overviewPresenterCallback = overviewPresenterCallbackNull;
  }

  public interface RepositoriesPresenterCallback {
    void willLoadRepositories();

    void repositoriesLoaded(List<RepositoryResponse> responses);

    void didLoadRepositories();

    class NullView implements RepositoriesPresenterCallback {

      @Override
      public void willLoadRepositories() {

      }

      @Override
      public void repositoriesLoaded(List<RepositoryResponse> responses) {

      }

      @Override
      public void didLoadRepositories() {

      }
    }
  }
}
