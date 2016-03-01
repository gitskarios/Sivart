package com.alorma.travisdk.interactor.accounts;

import com.alorma.travisdk.bean.utils.Credential;
import java.util.List;

public interface GetCredentialsInteractor {
  List<Credential> getCredentials();
}
