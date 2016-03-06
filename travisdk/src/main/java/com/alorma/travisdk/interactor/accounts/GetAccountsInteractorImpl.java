package com.alorma.travisdk.interactor.accounts;

import com.alorma.travisdk.bean.response.GithubAccount;
import com.alorma.travisdk.repository.accounts.GetAccountsRepository;
import java.util.List;
import rx.Observable;

public class GetAccountsInteractorImpl implements GetAccountsInteractor {

  private GetAccountsRepository repository;

  public GetAccountsInteractorImpl(GetAccountsRepository repository) {
    this.repository = repository;
  }

  @Override
  public Observable<List<GithubAccount>> accounts() {
    return repository.getAccounts();
  }
}
