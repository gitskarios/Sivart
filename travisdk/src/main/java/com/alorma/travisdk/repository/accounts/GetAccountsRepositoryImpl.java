package com.alorma.travisdk.repository.accounts;

import com.alorma.travisdk.bean.response.GithubAccount;
import com.alorma.travisdk.datasource.accounts.AccountsDataSource;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;

public class GetAccountsRepositoryImpl implements GetAccountsRepository {

  private AccountsDataSource cache;
  private AccountsDataSource user;
  private AccountsDataSource orgs;

  public GetAccountsRepositoryImpl(AccountsDataSource cache, AccountsDataSource user,
      AccountsDataSource orgs) {
    this.cache = cache;
    this.user = user;
    this.orgs = orgs;
  }

  @Override
  public Observable<List<GithubAccount>> getAccounts() {
    Observable<List<GithubAccount>> api = Observable.zip(user.getAccounts(), orgs.getAccounts(), (accounts1, accounts2) -> {
      List<GithubAccount> accountList = new ArrayList<>();
      if (accounts1 != null) {
        accountList.addAll(accounts1);
      }
      if (accounts2 != null) {
        accountList.addAll(accounts2);
      }
      return accountList;
    })
        .doOnNext(accounts -> cache.save(accounts));
    return Observable.concat(cache.getAccounts(), api).filter(accounts -> accounts != null);
  }
}
