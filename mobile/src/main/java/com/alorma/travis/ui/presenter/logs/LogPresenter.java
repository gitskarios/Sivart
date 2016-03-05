package com.alorma.travis.ui.presenter.logs;

import com.alorma.travis.ui.presenter.BasePresenter;
import com.alorma.travis.ui.presenter.utils.RetrofitWrapper;
import com.alorma.travisdk.bean.response.TravisJobResponse;
import com.alorma.travisdk.datasource.logs.GetLogDataSource;
import com.alorma.travisdk.datasource.logs.cache.CacheGetLogDataSource;
import com.alorma.travisdk.interactor.credentials.ActiveCredentialRepositoryImpl;
import com.alorma.travisdk.interactor.logs.GetJobLogInteractorImpl;
import com.alorma.travisdk.repository.logs.GetLogRepository;
import com.alorma.travisdk.repository.logs.GetLogRepositoryImpl;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LogPresenter extends BasePresenter {

  private final TravisJobResponse jobResponse;
  private LogCallback logCallbackNull = (log) -> {
  };
  private LogCallback logCallback = logCallbackNull;

  public LogPresenter(TravisJobResponse jobResponse) {
    this.jobResponse = jobResponse;
  }

  public void start() {
    GetLogDataSource cacheDataSource = new CacheGetLogDataSource();
    RetrofitWrapper retrofitWrapper = new RetrofitWrapper();
    GetLogDataSource apiDataSource = new ApiGetLogDataSource(retrofitWrapper);

    GetLogRepository repository = new GetLogRepositoryImpl(cacheDataSource, apiDataSource);

    Observable<String> logObservabe = new GetJobLogInteractorImpl(repository,
        ActiveCredentialRepositoryImpl.getInstance()).getLogObservable(jobResponse.getId());

    logObservabe.subscribeOn(Schedulers.newThread())
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
