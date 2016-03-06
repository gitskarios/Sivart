package com.alorma.travisdk.repository.logs;

import com.alorma.travisdk.repository.credentials.CredentialProvider;

public interface GetLogRepository extends CredentialProvider{
  String getLog(long jobId) throws Exception;
}
