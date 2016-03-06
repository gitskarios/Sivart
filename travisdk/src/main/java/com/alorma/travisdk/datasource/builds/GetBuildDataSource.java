package com.alorma.travisdk.datasource.builds;

import com.alorma.travisdk.bean.response.TravisBuild;
import com.alorma.travisdk.bean.response.TravisBuildResponse;
import com.alorma.travisdk.bean.response.TravisBuildsListResponse;
import com.alorma.travisdk.repository.credentials.CredentialProvider;

import java.util.List;
import rx.Observable;

public interface GetBuildDataSource extends CredentialProvider{
  Observable<TravisBuild> get(long repoId, long buildId);
  void save(long repoId, long buildId, TravisBuild build);

  Observable<TravisBuildsListResponse> get(String owner, String name);
  void save(String owner, String name, TravisBuildsListResponse build);
}
