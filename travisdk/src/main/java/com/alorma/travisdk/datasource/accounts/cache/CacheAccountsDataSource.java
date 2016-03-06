package com.alorma.travisdk.datasource.accounts.cache;

import com.alorma.travisdk.bean.response.GithubAccount;
import com.alorma.travisdk.datasource.accounts.AccountsDataSource;
import java.util.List;
import rx.Observable;

public class CacheAccountsDataSource implements AccountsDataSource {

  private final String name;
  private final String token;

  public CacheAccountsDataSource(String name, String token) {

    this.name = name;
    this.token = token;
  }

  @Override
  public Observable<List<GithubAccount>> getAccounts() {
    return Observable.just(CacheAccountsWrapper.get(name, token));
  }

  @Override
  public void save(List<GithubAccount> accounts) {
    CacheAccountsWrapper.set(name, token, accounts);
  }
}
