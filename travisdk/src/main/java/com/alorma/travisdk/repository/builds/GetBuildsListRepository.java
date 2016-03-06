package com.alorma.travisdk.repository.builds;

import com.alorma.travisdk.bean.response.TravisBuildsListResponse;
import com.alorma.travisdk.repository.credentials.CredentialProvider;
import rx.Observable;

public interface GetBuildsListRepository extends CredentialProvider {
  Observable<TravisBuildsListResponse> get(String owner, String name);
}
