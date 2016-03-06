package com.alorma.travisdk.interactor.builds;

import com.alorma.travisdk.bean.response.TravisBuildResponse;
import com.alorma.travisdk.bean.response.TravisBuildsListResponse;
import java.util.List;
import rx.Observable;

public interface GetBuildsListInteractor {
  Observable<List<TravisBuildResponse>> get(String owner, String name);
}
