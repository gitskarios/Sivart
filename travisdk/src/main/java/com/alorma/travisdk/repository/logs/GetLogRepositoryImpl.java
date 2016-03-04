package com.alorma.travisdk.repository.logs;

import com.alorma.travisdk.datasource.logs.GetLogDataSource;

public class GetLogRepositoryImpl implements GetLogRepository {
  private final GetLogDataSource cache;
  private final GetLogDataSource api;

  public GetLogRepositoryImpl(GetLogDataSource cache, GetLogDataSource api) {
    this.cache = cache;
    this.api = api;
  }

  @Override
  public String getLog(long jobId) throws Exception {
    String log = cache.getLog(jobId);
    if (log == null) {
      log = api.getLog(jobId);
    }
    return log;
  }
}
