package com.alorma.travisdk.repository.logs;

public interface GetLogRepository {
  String getLog(long jobId) throws Exception;
}
