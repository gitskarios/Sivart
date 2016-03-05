package com.alorma.travisdk.repository.repos;

import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.datasource.repos.TravisRepositoriesDataSource;
import java.util.List;
import rx.Observable;

public class TravisRepositoriesRepositoryImpl implements TravisRepositoriesRepository {
  private final TravisRepositoriesDataSource cacheDatasource;
  private final TravisRepositoriesDataSource apiDatasource;

  public TravisRepositoriesRepositoryImpl(TravisRepositoriesDataSource cacheDatasource,
      TravisRepositoriesDataSource apiDatasource) {

    this.cacheDatasource = cacheDatasource;
    this.apiDatasource = apiDatasource;
  }

  @Override
  public Observable<List<RepositoryResponse>> getRepos(String owner, boolean active) {
    Observable<List<RepositoryResponse>> cache = cacheDatasource.getRepos(owner, active);
    Observable<List<RepositoryResponse>> api = apiDatasource.getRepos(owner, active)
        .doOnNext(responses -> cacheDatasource.save(owner, responses));
    return Observable.concat(cache, api).filter(responses -> responses != null);
  }

  @Override
  public void setCredential(Credential credential) {
    apiDatasource.setCredential(credential);
  }
}
