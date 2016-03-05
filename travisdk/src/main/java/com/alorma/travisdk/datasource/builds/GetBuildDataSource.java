package com.alorma.travisdk.datasource.builds;

import com.alorma.travisdk.bean.response.TravisBuild;
import com.alorma.travisdk.repository.auth.CredentialProvider;
import rx.Observable;

public interface GetBuildDataSource extends CredentialProvider{
  Observable<TravisBuild> get(long repoId, long buildId);
  void save(long repoId, long buildId, TravisBuild build);
}
