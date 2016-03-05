package com.alorma.travisdk.interactor.repos;

import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.interactor.accounts.ActiveCredentialRepository;
import com.alorma.travisdk.repository.repos.TravisRepositoriesRepository;
import java.util.Collections;
import java.util.List;
import rx.Observable;

public class GetTravisRepositoriesInteractorImpl implements ReposInteractor {
  private final TravisRepositoriesRepository travisRepositoriesRepository;
  private ActiveCredentialRepository credentialRepository;

  public GetTravisRepositoriesInteractorImpl(
      TravisRepositoriesRepository travisRepositoriesRepository,
      ActiveCredentialRepository credentialRepository) {
    this.travisRepositoriesRepository = travisRepositoriesRepository;
    this.credentialRepository = credentialRepository;
  }

  @Override
  public Observable<List<RepositoryResponse>> getRepos(String owner, boolean active) {
    return credentialRepository.getCredential().flatMap(credential -> {
      travisRepositoriesRepository.setCredential(credential);
      return travisRepositoriesRepository.getRepos(owner, active);
    });
  }
}
