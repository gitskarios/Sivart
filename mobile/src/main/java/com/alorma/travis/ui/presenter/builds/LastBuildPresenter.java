package com.alorma.travis.ui.presenter.builds;

import com.alorma.travis.ui.presenter.BasePresenter;
import com.alorma.travis.ui.presenter.utils.RetrofitWrapper;
import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.bean.response.TravisBuild;
import com.alorma.travisdk.datasource.builds.GetBuildDataSource;
import com.alorma.travisdk.datasource.builds.cache.CacheGetBuildDataSource;
import com.alorma.travisdk.interactor.accounts.ActiveCredentialRepositoryImpl;
import com.alorma.travisdk.interactor.builds.GetBuildInteractor;
import com.alorma.travisdk.interactor.builds.GetBuildInteractorImpl;
import com.alorma.travisdk.repository.builds.GetBuildRepository;
import com.alorma.travisdk.repository.builds.GetBuildRepositoryImpl;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LastBuildPresenter extends BasePresenter {

  private LastBuildCallback lastBuildCallbackNull = new LastBuildCallback.NullView();
  private LastBuildCallback lastBuildCallback = lastBuildCallbackNull;
  private Subscription subscription;

  public void start(RepositoryResponse repositoryResponse) {
    GetBuildDataSource api =
        new ApiGetBuildDataSource(new RetrofitWrapper());

    GetBuildDataSource cache = new CacheGetBuildDataSource();

    GetBuildRepository repository = new GetBuildRepositoryImpl(cache, api);
    GetBuildInteractor interactor = new GetBuildInteractorImpl(repository, ActiveCredentialRepositoryImpl
        .getInstance());

    subscription = Observable.create(
        new BuildSubscriber(interactor, repositoryResponse.getId(), repositoryResponse.lastBuildId))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(() -> lastBuildCallback.willLoadBuild())
        .doOnCompleted(() -> lastBuildCallback.didLoadBuild())
        .subscribe(build -> {
          lastBuildCallback.buildLoaded(build);
        }, Throwable::printStackTrace);
  }

  public void stop() {
    if (subscription != null) {
      subscription.unsubscribe();
      subscription = null;
    }
    lastBuildCallback = lastBuildCallbackNull;
  }

  public void setLastBuildCallback(LastBuildCallback lastBuildCallback) {
    this.lastBuildCallback = lastBuildCallback;
  }

  public interface LastBuildCallback {
    void willLoadBuild();

    void buildLoaded(TravisBuild build);

    void didLoadBuild();

    class NullView implements LastBuildCallback {
      @Override
      public void willLoadBuild() {

      }

      @Override
      public void buildLoaded(TravisBuild build) {

      }

      @Override
      public void didLoadBuild() {

      }
    }
  }
}
