package com.alorma.travis.ui.presenter.builds;

import com.alorma.travis.ui.presenter.BasePresenter;
import com.alorma.travis.ui.presenter.utils.RetrofitWrapper;
import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.bean.response.TravisBuildResponse;
import com.alorma.travisdk.datasource.builds.GetBuildDataSource;
import com.alorma.travisdk.datasource.builds.cache.CacheGetBuildDataSource;
import com.alorma.travisdk.interactor.builds.GetBuildsListInteractor;
import com.alorma.travisdk.interactor.builds.GetBuildsListInteractorImpl;
import com.alorma.travisdk.interactor.credentials.ActiveCredentialRepositoryImpl;
import com.alorma.travisdk.repository.builds.GetBuildsListRepository;
import com.alorma.travisdk.repository.builds.GetBuildsListRepositoryImpl;
import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BuildListPresenter extends BasePresenter {

  private BuildsCallback buildsCallbackNull = new BuildsCallback.NullView();
  private BuildsCallback buildsCallback = buildsCallbackNull;
  private Subscription subscription;

  public void start(RepositoryResponse repositoryResponse) {
    GetBuildDataSource api = new ApiGetBuildDataSource(new RetrofitWrapper());

    GetBuildDataSource cache = new CacheGetBuildDataSource();

    GetBuildsListRepository repository = new GetBuildsListRepositoryImpl(cache, api);
    GetBuildsListInteractor interactor =
        new GetBuildsListInteractorImpl(repository, ActiveCredentialRepositoryImpl.getInstance());

    subscription = interactor.get(repositoryResponse.getOwner(), repositoryResponse.getRepo())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(() -> buildsCallback.willLoadBuilds())
        .doOnCompleted(() -> buildsCallback.didLoadBuilds())
        .subscribe(builds -> {
          buildsCallback.buildsLoaded(builds);
        }, Throwable::printStackTrace);
  }

  public void stop() {
    if (subscription != null) {
      subscription.unsubscribe();
      subscription = null;
    }
    buildsCallback = buildsCallbackNull;
  }

  public void setBuildsCallback(BuildsCallback buildsCallback) {
    this.buildsCallback = buildsCallback;
  }

  public interface BuildsCallback {
    void willLoadBuilds();

    void buildsLoaded(List<TravisBuildResponse> builds);

    void didLoadBuilds();

    class NullView implements BuildsCallback {

      @Override
      public void willLoadBuilds() {

      }

      @Override
      public void buildsLoaded(List<TravisBuildResponse> builds) {

      }

      @Override
      public void didLoadBuilds() {

      }
    }
  }
}
