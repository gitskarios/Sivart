package com.alorma.travisdk.datasource.logs;

import com.alorma.travisdk.repository.auth.CredentialProvider;

public interface GetLogDataSource extends CredentialProvider{
  String getLog(long jobId) throws Exception;
}
