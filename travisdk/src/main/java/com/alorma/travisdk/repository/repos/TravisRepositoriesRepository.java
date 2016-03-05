package com.alorma.travisdk.repository.repos;

import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.repository.auth.CredentialProvider;
import java.util.List;
import rx.Observable;

public interface TravisRepositoriesRepository extends CredentialProvider{
  Observable<List<RepositoryResponse>> getRepos(String owner, boolean active);
}
