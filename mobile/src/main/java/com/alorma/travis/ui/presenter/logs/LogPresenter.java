package com.alorma.travis.ui.presenter.logs;

import com.alorma.travis.ui.presenter.BasePresenter;
import com.alorma.travis.ui.presenter.utils.RetrofitWrapper;
import com.alorma.travisdk.bean.response.TravisJobResponse;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.datasource.logs.GetLogDataSource;
import com.alorma.travisdk.datasource.logs.cache.CacheGetLogDataSource;
import com.alorma.travisdk.interactor.accounts.ActiveCredentialRepository;
import com.alorma.travisdk.interactor.accounts.ActiveCredentialRepositoryImpl;
import com.alorma.travisdk.interactor.logs.GetJobLogInteractorImpl;
import com.alorma.travisdk.repository.logs.GetLogRepository;
import com.alorma.travisdk.repository.logs.GetLogRepositoryImpl;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LogPresenter extends BasePresenter {

  private Credential credential;
  private final TravisJobResponse jobResponse;
  private LogCallback logCallbackNull = (log) -> {
  };
  private LogCallback logCallback = logCallbackNull;

  public LogPresenter(Credential credential, TravisJobResponse jobResponse) {
    this.credential = credential;
    this.jobResponse = jobResponse;
    setupGithubToken(credential.getGithubToken());
    setupGithubUrl(credential.getGithubUrl());
  }

  public void start() {
    GetLogDataSource cacheDataSource = new CacheGetLogDataSource();
    RetrofitWrapper retrofitWrapper = new RetrofitWrapper();
    GetLogDataSource apiDataSource =
        new ApiGetLogDataSource(retrofitWrapper, credential.getTravisUrl(), credential.getToken());

    GetLogRepository repository = new GetLogRepositoryImpl(cacheDataSource, apiDataSource);
    Observable.create(new LogSubscriber(new GetJobLogInteractorImpl(repository), jobResponse))
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(o -> logCallback.onLogLoaded(o), Throwable::printStackTrace);
  }

  public void setLogCallback(LogCallback logCallback) {
    this.logCallback = logCallback;
  }

  public interface LogCallback {
    void onLogLoaded(String log);
  }
}
