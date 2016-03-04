package com.alorma.travisdk.datasource.logs.cloud;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LogService {

  @GET("/jobs/{job_id}/log")
  Call<ResponseBody> getLog(@Path("job_id") long jobId);

}
