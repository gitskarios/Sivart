package com.alorma.travisdk.repository.repos;

import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.datasource.repos.TravisRepositoriesDataSource;
import java.util.List;

public class TravisRepositoriesRepositoryImpl implements TravisRepositoriesRepository {
  private final TravisRepositoriesDataSource cacheDatasource;
  private final TravisRepositoriesDataSource apiDatasource;

  public TravisRepositoriesRepositoryImpl(TravisRepositoriesDataSource cacheDatasource,
      TravisRepositoriesDataSource apiDatasource) {

    this.cacheDatasource = cacheDatasource;
    this.apiDatasource = apiDatasource;
  }

  @Override
  public List<RepositoryResponse> getRepos(String owner, boolean active) throws Exception {
    List<RepositoryResponse> repos;
    repos = cacheDatasource.getRepos(owner, active);

    if (repos == null) {
      repos = apiDatasource.getRepos(owner, active);
      cacheDatasource.save(owner, repos);
    }
    return repos;
  }
}
