package com.alorma.travisdk.datasource.accounts;

import com.alorma.travisdk.bean.response.GithubAccount;
import java.util.List;
import rx.Observable;

public interface AccountsDataSource {
  Observable<List<GithubAccount>> getAccounts();
  void save(List<GithubAccount> accounts);
}
