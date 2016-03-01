package com.alorma.travisdk.datasource.repos;

import com.alorma.travisdk.bean.response.RepositoryResponse;
import java.util.List;

public interface TravisRepositoriesDataSource {
  List<RepositoryResponse> getRepos(String owner, boolean active) throws Exception;

  void save(String owner, List<RepositoryResponse> repos);
}
