package com.alorma.travisdk.datasource.logs;

public interface GetLogDataSource {
  String getLog(long jobId) throws Exception;
}
