package com.alorma.travisdk.repository.builds;

import com.alorma.travisdk.bean.response.TravisBuild;
import com.alorma.travisdk.repository.auth.CredentialProvider;
import rx.Observable;

public interface GetBuildRepository extends CredentialProvider{
  Observable<TravisBuild> get(long repoId, long buildId);
}
