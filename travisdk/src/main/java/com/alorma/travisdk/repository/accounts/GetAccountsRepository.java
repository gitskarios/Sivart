package com.alorma.travisdk.repository.accounts;

import com.alorma.travisdk.bean.response.GithubAccount;
import java.util.List;
import rx.Observable;

public interface GetAccountsRepository {
  Observable<List<GithubAccount>> getAccounts();
}
