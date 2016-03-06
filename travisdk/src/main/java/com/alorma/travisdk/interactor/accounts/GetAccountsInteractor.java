package com.alorma.travisdk.interactor.accounts;

import com.alorma.travisdk.bean.response.GithubAccount;
import java.util.List;
import rx.Observable;

public interface GetAccountsInteractor {

  Observable<List<GithubAccount>> accounts();

}
