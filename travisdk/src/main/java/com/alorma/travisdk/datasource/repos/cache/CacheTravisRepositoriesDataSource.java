package com.alorma.travisdk.datasource.repos.cache;

import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.datasource.repos.TravisRepositoriesDataSource;
import java.util.List;

public class CacheTravisRepositoriesDataSource implements TravisRepositoriesDataSource {
  @Override
  public List<RepositoryResponse> getRepos(String owner, boolean active) throws Exception {
    return CacheReposWrapper.get(owner);
  }

  @Override
  public void save(String owner, List<RepositoryResponse> repos) {
    CacheReposWrapper.set(owner, repos);
  }
}
