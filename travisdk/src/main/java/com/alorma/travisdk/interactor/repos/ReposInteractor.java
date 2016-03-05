package com.alorma.travisdk.interactor.repos;

import com.alorma.travisdk.bean.response.RepositoryResponse;
import java.util.List;
import rx.Observable;

public interface ReposInteractor {
  Observable<List<RepositoryResponse>> getRepos(String owner, boolean active);
}
