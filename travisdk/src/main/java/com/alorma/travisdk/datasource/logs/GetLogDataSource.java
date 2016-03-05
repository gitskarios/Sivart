package com.alorma.travisdk.datasource.logs;

import com.alorma.travisdk.repository.credentials.CredentialProvider;

public interface GetLogDataSource extends CredentialProvider{
  String getLog(long jobId) throws Exception;
}
