package com.alorma.travisdk.repository.builds;

import com.alorma.travisdk.bean.response.TravisBuildResponse;
import com.alorma.travisdk.repository.credentials.CredentialProvider;
import java.util.List;
import rx.Observable;

public interface GetBuildsListRepository extends CredentialProvider {
  Observable<List<TravisBuildResponse>> get(String owner, String name);
}
