package com.alorma.travisdk.repository.repos;

import com.alorma.travisdk.bean.response.RepositoryResponse;
import java.util.List;

public interface TravisRepositoriesRepository {
  List<RepositoryResponse> getRepos(String owner, boolean active) throws Exception;
}
