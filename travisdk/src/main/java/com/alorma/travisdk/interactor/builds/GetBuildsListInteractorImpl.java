package com.alorma.travisdk.interactor.builds;

import com.alorma.travisdk.bean.response.TravisBuildResponse;
import com.alorma.travisdk.bean.response.TravisCommitResponse;
import com.alorma.travisdk.interactor.credentials.ActiveCredentialRepository;
import com.alorma.travisdk.repository.builds.GetBuildsListRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    }).map(travisBuildResponses -> {

      List<TravisBuildResponse> builds = travisBuildResponses.getBuilds();
      List<TravisCommitResponse> commits = travisBuildResponses.getCommits();

      Map<Long, TravisCommitResponse> commitResponseMap = new HashMap<>();
      for (TravisCommitResponse commitResponse : commits) {
        commitResponseMap.put(commitResponse.getId(), commitResponse);
      }

      for (TravisBuildResponse build : builds) {
        build.setCommit(commitResponseMap.get(build.getCommitId()));
      }

      return builds;
    });
  }
}
