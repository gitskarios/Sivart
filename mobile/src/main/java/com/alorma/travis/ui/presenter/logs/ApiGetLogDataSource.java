package com.alorma.travis.ui.presenter.logs;

import com.alorma.travis.ui.presenter.utils.RetrofitWrapper;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.datasource.logs.GetLogDataSource;
import com.alorma.travisdk.datasource.logs.cloud.LogService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ApiGetLogDataSource implements GetLogDataSource {

  private final RetrofitWrapper retrofit;
  private Credential credential;

  public ApiGetLogDataSource(RetrofitWrapper retrofit) {
    this.retrofit = retrofit;
  }

  @Override
  public String getLog(long jobId) throws Exception {
    Call<ResponseBody> call = retrofit.accept("text/plain")
        .acceptParam(" chuncked=true")
        .getRetrofit(credential.getTravisUrl(), credential.getToken())
        .create(LogService.class)
        .getLog(jobId);

    Response<ResponseBody> response = call.execute();
    if (response.isSuccess()) {
      return response.body().string();
    } else {
      throw new Exception(response.errorBody().string());
    }
  }

  @Override
  public void setCredential(Credential credential) {

    this.credential = credential;
  }
}
