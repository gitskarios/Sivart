package com.alorma.travisdk.datasource.repos;

import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.repository.auth.CredentialProvider;
import java.util.List;
import rx.Observable;

public interface TravisRepositoriesDataSource extends CredentialProvider{
  Observable<List<RepositoryResponse>> getRepos(String owner, boolean active);

  void save(String owner, List<RepositoryResponse> repos);
}
