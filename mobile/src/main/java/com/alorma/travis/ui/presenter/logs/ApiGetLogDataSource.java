package com.alorma.travis.ui.presenter.logs;

import com.alorma.travis.ui.presenter.utils.RetrofitWrapper;
import com.alorma.travisdk.datasource.logs.GetLogDataSource;
import com.alorma.travisdk.datasource.logs.cloud.LogService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ApiGetLogDataSource implements GetLogDataSource {

  private final RetrofitWrapper retrofit;
  private final String url;
  private String token;

  public ApiGetLogDataSource(RetrofitWrapper retrofit, String url, String token) {
    this.retrofit = retrofit;
    this.url = url;
    this.token = token;
  }

  @Override
  public String getLog(long jobId) throws Exception {
    Call<ResponseBody> call = retrofit.accept("text/plain")
        .acceptParam(" chuncked=true")
        .getRetrofit(url, token)
        .create(LogService.class)
        .getLog(jobId);

    Response<ResponseBody> response = call.execute();
    if (response.isSuccess()) {
      return response.body().string();
    } else {
      throw new Exception(response.errorBody().string());
    }
  }
}
