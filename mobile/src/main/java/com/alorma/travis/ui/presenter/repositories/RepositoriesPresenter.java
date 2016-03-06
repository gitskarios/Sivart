package com.alorma.travis.ui.presenter.repositories;

import com.alorma.travis.ui.presenter.BasePresenter;
import com.alorma.travis.ui.presenter.overview.ApiTravisRepositoriesDataSource;
import com.alorma.travis.ui.presenter.utils.RetrofitWrapper;
import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.datasource.repos.TravisRepositoriesDataSource;
import com.alorma.travisdk.datasource.repos.cache.CacheTravisRepositoriesDataSource;
import com.alorma.travisdk.interactor.credentials.ActiveCredentialRepositoryImpl;
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

  private Subscription subscription;

  public RepositoriesPresenter() {

  }

  public void loadRepositories(String name) {
    subscription =
        createLoader(name).observeOn(
            AndroidSchedulers.mainThread())
            .doOnSubscribe(() -> overviewPresenterCallback.willLoadRepositories())
            .doOnCompleted(() -> overviewPresenterCallback.didLoadRepositories())
            .subscribe(list -> {
              overviewPresenterCallback.repositoriesLoaded(list);
            }, Throwable::printStackTrace);
  }

  private Observable<List<RepositoryResponse>> createLoader(String owner) {
    TravisRepositoriesDataSource cache = new CacheTravisRepositoriesDataSource();
    TravisRepositoriesDataSource api =
        new ApiTravisRepositoriesDataSource(new RetrofitWrapper());

    TravisRepositoriesRepository travisRepository =
        new TravisRepositoriesRepositoryImpl(cache, api);

    GetTravisRepositoriesInteractorImpl travisRepositoriesInteractor =
        new GetTravisRepositoriesInteractorImpl(travisRepository,
            ActiveCredentialRepositoryImpl.getInstance());

    return travisRepositoriesInteractor.getRepos(owner, true).subscribeOn(Schedulers.newThread());
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
