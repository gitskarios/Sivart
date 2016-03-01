package com.alorma.travisdk.interactor.repos;

import com.alorma.travisdk.bean.response.RepositoryResponse;
import java.util.List;

public interface ReposInteractor {
  List<RepositoryResponse> getRepos(String owner, boolean active) throws Exception;
}
