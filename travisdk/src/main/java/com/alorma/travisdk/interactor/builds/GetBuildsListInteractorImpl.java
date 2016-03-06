package com.alorma.travisdk.interactor.builds;

import com.alorma.travisdk.bean.response.TravisBuildResponse;
import com.alorma.travisdk.interactor.credentials.ActiveCredentialRepository;
import com.alorma.travisdk.repository.builds.GetBuildsListRepository;
import java.util.List;
import rx.Observable;

public class GetBuildsListInteractorImpl implements GetBuildsListInteractor {

  private GetBuildsListRepository repository;
  private ActiveCredentialRepository credentialRepository;

  public GetBuildsListInteractorImpl(GetBuildsListRepository repository,
      ActiveCredentialRepository credentialRepository) {
    this.repository = repository;
    this.credentialRepository = credentialRepository;
  }

  @Override
  public Observable<List<TravisBuildResponse>> get(String owner, String name) {
    if (owner == null || owner.isEmpty()) {
      throw new IllegalArgumentException("Repository owner cannot be empty");
    }

    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Repository name cannot be empty");
    }

    return credentialRepository.getCredential().flatMap(credential -> {
      repository.setCredential(credential);
      return repository.get(owner, name);
    });
  }
}
