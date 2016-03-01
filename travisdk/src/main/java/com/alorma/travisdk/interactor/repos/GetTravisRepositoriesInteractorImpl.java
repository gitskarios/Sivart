package com.alorma.travisdk.interactor.repos;

import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.repository.repos.TravisRepositoriesRepository;
import java.util.Collections;
import java.util.List;

public class GetTravisRepositoriesInteractorImpl implements ReposInteractor {
  private final TravisRepositoriesRepository travisRepositoriesRepository;

  public GetTravisRepositoriesInteractorImpl(
      TravisRepositoriesRepository travisRepositoriesRepository) {
    this.travisRepositoriesRepository = travisRepositoriesRepository;
  }

  @Override
  public List<RepositoryResponse> getRepos(String owner, boolean active) throws Exception {
    List<RepositoryResponse> repos = this.travisRepositoriesRepository.getRepos(owner, active);
    if (repos == null) {
      repos = Collections.emptyList();
    }
    return repos;
  }
}
