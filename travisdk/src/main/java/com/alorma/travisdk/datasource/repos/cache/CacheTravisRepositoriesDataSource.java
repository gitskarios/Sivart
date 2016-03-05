package com.alorma.travisdk.datasource.repos.cache;

import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.datasource.repos.TravisRepositoriesDataSource;
import java.util.List;
import rx.Observable;

public class CacheTravisRepositoriesDataSource implements TravisRepositoriesDataSource {
  @Override
  public Observable<List<RepositoryResponse>> getRepos(String owner, boolean active) {
    return Observable.just(CacheReposWrapper.get(owner));
  }

  @Override
  public void save(String owner, List<RepositoryResponse> repos) {
    CacheReposWrapper.set(owner, repos);
  }

  @Override
  public void setCredential(Credential credential) {

  }
}
